package edu.ucne.registrojugador.presentation.jugador.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.usecase.DeleteJugadorUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.GetJugadorUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.UpsertJugadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

@HiltViewModel
class EditJugadorViewModel @Inject constructor(
    private val upsertJugadorUseCase: UpsertJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase,
    private val getJugadorUseCase: GetJugadorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditJugadorUiState())
    val state: StateFlow<EditJugadorUiState> = _state.asStateFlow()

    fun onEvent(event: EditJugadorUiEvent) {
        when (event) {
            is EditJugadorUiEvent.NombresChanged ->
                _state.update { it.copy(nombres = event.nombres, nombresError = null) }

            is EditJugadorUiEvent.PartidasChanged ->
                _state.update { it.copy(partidas = event.partidas, partidasError = null) }

            is EditJugadorUiEvent.Load ->
                loadJugador(event.jugadorId)

            EditJugadorUiEvent.Save -> saveJugador()
            EditJugadorUiEvent.Delete -> deleteJugador()
        }
    }

    private fun loadJugador(jugadorId: Int) {
        viewModelScope.launch {
            if (jugadorId == 0) {
                // Nuevo jugador
                _state.update { it.copy(isNew = true, jugadorId = null) }
            } else {
                // Cargar jugador existente
                val jugador = getJugadorUseCase(jugadorId)
                jugador?.let {
                    _state.update {
                        it.copy(
                            isNew = false,
                            jugadorId = jugador.jugadorId,
                            nombres = jugador.nombres,
                            partidas = jugador.partidas.toString()
                        )
                    }
                }
            }
        }
    }

    private fun validateNombres(nombres: String): ValidationResult =
        if (nombres.isBlank()) ValidationResult(false, "El nombre es obligatorio")
        else ValidationResult(true)

    private fun validatePartidas(partidas: String): ValidationResult {
        val partidasNum = partidas.toIntOrNull()
        return when {
            partidas.isBlank() -> ValidationResult(false, "Las partidas son obligatorias")
            partidasNum == null || partidasNum < 0 -> ValidationResult(false, "Debe ser un número entero mayor o igual a 0")
            else -> ValidationResult(true)
        }
    }

    private fun saveJugador() {
        val currentState = state.value
        val nombresResult = validateNombres(currentState.nombres)
        val partidasResult = validatePartidas(currentState.partidas)

        if (!nombresResult.successful || !partidasResult.successful) {
            _state.update {
                it.copy(
                    nombresError = nombresResult.errorMessage,
                    partidasError = partidasResult.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            try {
                // Correctly get the validated number after a successful check
                val partidasInt = currentState.partidas.toInt()

                // Crear jugador a guardar
                val jugador = Jugador(
                    jugadorId = currentState.jugadorId ?: 0,
                    nombres = currentState.nombres,
                    partidas = partidasInt
                )

                // Verificar duplicado
                val duplicateResult = upsertJugadorUseCase.checkDuplicate(jugador)
                if (duplicateResult.isFailure) {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            nombresError = duplicateResult.exceptionOrNull()?.message
                        )
                    }
                    return@launch
                }

                // Guardar jugador
                upsertJugadorUseCase(jugador)
                    .onFailure { ex ->
                        _state.update { it.copy(isSaving = false, nombresError = ex.message) }
                        return@launch
                    }

                _state.update {
                    it.copy(isSaving = false, message = "Jugador guardado correctamente")
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(isSaving = false, message = "Error al guardar: ${e.message}")
                }
            }
        }
    }

    private fun deleteJugador() {
        val id = state.value.jugadorId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteJugadorUseCase(id)
                _state.update { it.copy(isDeleting = false, message = "Jugador eliminado correctamente") }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false, message = "Error al eliminar: ${e.message}") }
            }
        }
    }

    fun clearMessage() {
        _state.update { it.copy(message = null) }
    }
}
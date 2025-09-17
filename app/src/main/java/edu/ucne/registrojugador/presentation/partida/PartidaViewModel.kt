package edu.ucne.registrojugador.presentation.partida

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.usecase.partida.DeletePartidaUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.partida.ExistePartidaUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.partida.ObservePartidaUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.partida.UpsertPartidaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartidaViewModel @Inject constructor(
    private val observePartidasUseCase: ObservePartidaUseCase,
    private val upsertPartidaUseCase: UpsertPartidaUseCase,
    private val deletePartidaUseCase: DeletePartidaUseCase,
    private val existePartidaUseCase: ExistePartidaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PartidaUiState())
    val state: StateFlow<PartidaUiState> = _state.asStateFlow()

    init {
        observePartidas()
    }

    private fun observePartidas() {
        viewModelScope.launch {
            observePartidasUseCase().collect { partidas: List<Partida> ->
                _state.update { it.copy(partidas = partidas) }
            }
        }
    }

    fun agregarPartida(partida: Partida) {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            try {
                // Verificar si ya existe
                if (existePartidaUseCase(partida.jugador1Id, partida.jugador2Id, partida.fecha)) {
                    _state.update { it.copy(isSaving = false, message = "Esta partida ya existe") }
                    return@launch
                }

                upsertPartidaUseCase(partida)
                _state.update { it.copy(isSaving = false, message = "Partida guardada correctamente") }

            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, message = "Error al guardar: ${e.message}") }
            }
        }
    }

    fun eliminarPartida(partida: Partida) {
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }

            try {
                deletePartidaUseCase(partida)
                _state.update { it.copy(isDeleting = false, message = "Partida eliminada correctamente") }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false, message = "Error al eliminar: ${e.message}") }
            }
        }
    }

    fun clearMessage() {
        _state.update { it.copy(message = null) }
    }
}
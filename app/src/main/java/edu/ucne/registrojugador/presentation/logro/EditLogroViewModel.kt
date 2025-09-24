package edu.ucne.registrojugador.presentation.logro.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugador.domain.jugador.model.Logro
import edu.ucne.registrojugador.domain.logro.usecase.GetLogroUseCase
import edu.ucne.registrojugador.domain.logro.usecase.UpsertLogroUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditLogroViewModel @Inject constructor(
    private val getLogroUseCase: GetLogroUseCase,
    private val upsertLogroUseCase: UpsertLogroUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = mutableStateOf(EditLogroUiState())
    val uiState: State<EditLogroUiState> = _uiState

    init {
        val logroId = savedStateHandle.get<String>("logroId")?.toIntOrNull() ?: 0
        if (logroId != 0) {
            loadLogro(logroId)
        }
    }

    private fun loadLogro(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val logro = getLogroUseCase(id)
            if (logro != null) {
                _uiState.value = _uiState.value.copy(
                    logroId = logro.logroId,
                    nombre = logro.nombre,
                    descripcion = logro.descripcion,
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Logro no encontrado"
                )
            }
        }
    }

    fun onEvent(event: EditLogroUiEvent) {
        when (event) {
            is EditLogroUiEvent.OnNombreChanged -> _uiState.value =
                _uiState.value.copy(nombre = event.nombre)
            is EditLogroUiEvent.OnDescripcionChanged -> _uiState.value =
                _uiState.value.copy(descripcion = event.descripcion)
            is EditLogroUiEvent.OnSaveClicked -> saveLogro()
        }
    }

    private fun saveLogro() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val logroToSave = Logro(
                    logroId = _uiState.value.logroId ?: 0,
                    nombre = _uiState.value.nombre,
                    descripcion = _uiState.value.descripcion
                )
                upsertLogroUseCase(logroToSave)
                _uiState.value = _uiState.value.copy(isLoading = false, isSaved = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al guardar el logro"
                )
            }
        }
    }
}

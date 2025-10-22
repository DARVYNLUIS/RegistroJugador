package edu.ucne.registrojugador.presentation.partida


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugador.data.api.RetrofitClient
import edu.ucne.registrojugador.data.local.dto.Movimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovimientosUiState(
    val movimientos: List<Movimiento> = emptyList(),
    val message: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class MovimientosViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MovimientosUiState())
    val state: StateFlow<MovimientosUiState> = _state.asStateFlow()

    fun loadMovimientos(partidaId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            try {
                val movimientos = RetrofitClient.apiService.getMovimientos(partidaId)
                _state.update { it.copy(movimientos = movimientos, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, message = "Error al cargar movimientos: ${e.localizedMessage}") }
            }
        }
    }

    fun agregarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            try {
                val response = RetrofitClient.apiService.postMovimiento(movimiento)
                if (response.isSuccessful) {
                    _state.update {
                        it.copy(
                            movimientos = it.movimientos + movimiento,
                            isLoading = false,
                            message = "Movimiento enviado correctamente"
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = "Error al enviar movimiento: ${response.code()}"
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = "Fallo en la conexion: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    fun clearMessage() {
        _state.update { it.copy(message = null) }
    }
}

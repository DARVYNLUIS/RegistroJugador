package edu.ucne.registrojugador.presentation.logro.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugador.domain.logro.usecase.ObserveLogrosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListLogroViewModel @Inject constructor(
    private val observeLogrosUseCase: ObserveLogrosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListLogroUiState())
    val state: StateFlow<ListLogroUiState> = _state.asStateFlow()

    init {
        loadLogros()
    }

    private fun loadLogros() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                observeLogrosUseCase().collect { logros ->
                    _state.update { it.copy(logros = logros, isLoading = false, error = "") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "Error desconocido", isLoading = false) }
            }
        }
    }
}

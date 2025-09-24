package edu.ucne.registrojugador.presentation.logro.list

import edu.ucne.registrojugador.domain.jugador.model.Logro

data class ListLogroUiState(
    val logros: List<Logro> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
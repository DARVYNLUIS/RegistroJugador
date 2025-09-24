package edu.ucne.registrojugador.presentation.partida

import edu.ucne.registrojugador.domain.jugador.model.Partida

 data class PartidaUiState(
    val partidas: List<Partida> = emptyList(),
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val message: String? = null
)


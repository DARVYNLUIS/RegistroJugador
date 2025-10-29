package edu.ucne.registrojugador.presentation.list

import edu.ucne.registrojugador.domain.jugador.model.Player

data class ListPlayerState(
    val players: List<Player> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null
)
package edu.ucne.registrojugador.presentation.list

import edu.ucne.registrojugador.domain.jugador.model.Jugador

data class ListJugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)

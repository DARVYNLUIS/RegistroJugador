package edu.ucne.registrojugador.domain.jugador.model


data class Jugador(
    val JugadorId: Int? = null,
    val nombres: String = "",
    val partidas: List<Partida> = emptyList()
)

package edu.ucne.registrojugador.domain.jugador.model

data class Movimiento(
    val id: Int? = null,
    val partidaId: Int,
    val jugador: String,
    val posicionFila: Int,
    val posicionColumna: Int
)
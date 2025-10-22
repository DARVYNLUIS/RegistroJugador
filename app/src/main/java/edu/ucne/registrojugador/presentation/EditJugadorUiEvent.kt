package edu.ucne.registrojugador.presentation

sealed class EditJugadorUiEvent {
    data class NombresChanged(val nombres: String) : EditJugadorUiEvent()
    data class PartidasChanged(val partidas: String) : EditJugadorUiEvent()
    data class Load(val jugadorId: Int) : EditJugadorUiEvent()
    object Save : EditJugadorUiEvent()
    object Delete : EditJugadorUiEvent()
}
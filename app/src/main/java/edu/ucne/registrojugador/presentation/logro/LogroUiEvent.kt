package edu.ucne.registrojugador.presentation.logro.list

sealed class ListLogroUiEvent {
    data class OnLogroClicked(val logroId: Int) : ListLogroUiEvent()
}
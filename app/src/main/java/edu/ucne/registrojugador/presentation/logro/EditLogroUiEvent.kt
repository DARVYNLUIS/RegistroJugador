package edu.ucne.registrojugador.presentation.logro.edit

sealed class EditLogroUiEvent {
    data class OnNombreChanged(val nombre: String) : EditLogroUiEvent()
    data class OnDescripcionChanged(val descripcion: String) : EditLogroUiEvent()
    object OnSaveClicked : EditLogroUiEvent()
}
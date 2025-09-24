package edu.ucne.registrojugador.presentation.logro.edit

data class EditLogroUiState(
    val logroId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)
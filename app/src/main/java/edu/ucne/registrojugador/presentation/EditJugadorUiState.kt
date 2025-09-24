package edu.ucne.registrojugador.presentation.jugador.edit

data class EditJugadorUiState(
    val nombres: String = "",
    val partidas: String = "",
    val nombresError: String? = null,
    val partidasError: String? = null,
    val isNew: Boolean = true,
    val jugadorId: Int? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val message: String? = null
)
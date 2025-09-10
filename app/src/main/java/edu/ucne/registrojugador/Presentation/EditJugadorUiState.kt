package edu.ucne.registrojugador.presentation.jugador.edit

data class EditJugadorUiState(
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: Int? = null,
    val nombresError: String? = null,
    val partidasError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val message: String? = null
)

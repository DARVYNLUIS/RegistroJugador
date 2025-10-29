package edu.ucne.registrojugador.presentation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

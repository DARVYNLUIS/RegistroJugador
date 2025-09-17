// In file: ExistePorNombreUseCase.kt
package edu.ucne.registrojugador.domain.jugador.usecase

import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository // Import the interface
import javax.inject.Inject

class ExistePorNombreUseCase @Inject constructor(
    private val repository: JugadorRepository // Depend on the interface
) {
    suspend operator fun invoke(nombre: String): Boolean {
        return repository.existePorNombre(nombre)
    }
}
package edu.ucne.registrojugador.domain.jugador.usecase



import edu.ucne.registrojugador.data.repository.JugadorRepositoryImpl
import javax.inject.Inject

class ExistePorNombreUseCase @Inject constructor(
    private val repository: JugadorRepositoryImpl
) {
    suspend operator fun invoke(nombre: String): Boolean {
        return repository.existePorNombre(nombre)
    }
}
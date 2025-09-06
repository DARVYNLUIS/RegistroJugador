package edu.ucne.registrojugador.domain.jugador.usecase

import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository

class DeleteJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}

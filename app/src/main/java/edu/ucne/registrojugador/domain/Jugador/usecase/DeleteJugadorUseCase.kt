package edu.ucne.registrojugador.domain.jugador.usecase

import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import javax.inject.Inject // <-- Import this

class DeleteJugadorUseCase @Inject constructor( // <-- Add @Inject here
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
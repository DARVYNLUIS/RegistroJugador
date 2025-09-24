package edu.ucne.registrojugador.domain.jugador.usecase

import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import javax.inject.Inject // Import this

class GetJugadorUseCase @Inject constructor( // <-- Add @Inject here
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int): Jugador? = repository.getJugador(id)
}
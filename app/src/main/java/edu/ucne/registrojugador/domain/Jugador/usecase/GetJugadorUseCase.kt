package edu.ucne.registrojugador.domain.jugador.usecase

import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository

class GetJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int): Jugador? = repository.getJugador(id)
}

package edu.ucne.registrojugador.domain.jugador.usecase

import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow

class ObserveJugadorUseCase(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> = repository.observeJugadores()
}

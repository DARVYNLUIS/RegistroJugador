package edu.ucne.registrojugador.domain.jugador.usecase

import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject // <-- Import this

class ObserveJugadorUseCase @Inject constructor( // <-- Add @Inject here
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> = repository.observeJugadores()
}
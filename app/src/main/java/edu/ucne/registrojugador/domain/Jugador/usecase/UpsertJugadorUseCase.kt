package edu.ucne.registrojugador.domain.jugador.usecase

import kotlinx.coroutines.flow.first
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import javax.inject.Inject // <-- Add this import

class UpsertJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Int> {
        val existingJugadores = repository.observeJugadores().first()

        val duplicate = existingJugadores.any {
            it.nombres.equals(jugador.nombres, ignoreCase = true)
                    && it.JugadorId != jugador.JugadorId
        }

        if (duplicate) {
            return Result.failure(IllegalArgumentException("El nombre ya existe"))
        }

        return runCatching { repository.upsert(jugador) as Int }
    }

    suspend fun getJugadorById(jugadorId: Int): Jugador? {
        return repository.observeJugadores()
            .first()
            .firstOrNull { it.JugadorId == jugadorId }
    }

    suspend fun checkDuplicate(jugador: Jugador): Result<Unit> {
        val existingJugadores = repository.observeJugadores().first()
        val duplicate = existingJugadores.any {
            it.nombres.equals(jugador.nombres, ignoreCase = true)
                    && it.JugadorId != jugador.JugadorId
        }
        return if (duplicate) Result.failure(IllegalArgumentException("El nombre ya existe"))
        else Result.success(Unit)
    }
}
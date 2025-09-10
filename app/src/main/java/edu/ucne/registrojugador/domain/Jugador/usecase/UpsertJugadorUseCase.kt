package edu.ucne.registrojugador.domain.jugador.usecase

import kotlinx.coroutines.flow.first
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository

class UpsertJugadorUseCase(
    private val repository: JugadorRepository
) {

    suspend operator fun invoke(jugador: Jugador): Result<Int> {
        val existingJugadores = repository.observeJugadores().first()

        val duplicate = existingJugadores.any {
            it.nombres.equals(jugador.nombres, ignoreCase = true)
                    && it.jugadorId != jugador.jugadorId
        }

        if (duplicate) {
            return Result.failure(IllegalArgumentException("El nombre ya existe"))
        }

        return runCatching { repository.upsert(jugador) }
    }

    // Obtener un jugador por su ID
    suspend fun getJugadorById(jugadorId: Int): Jugador? {
        return repository.observeJugadores()
            .first()
            .firstOrNull { it.jugadorId == jugadorId }
    }

    suspend fun checkDuplicate(jugador: Jugador): Result<Unit> {
        val existingJugadores = repository.observeJugadores().first()
        val duplicate = existingJugadores.any {
            it.nombres.equals(jugador.nombres, ignoreCase = true)
                    && it.jugadorId != jugador.jugadorId
        }
        return if (duplicate) Result.failure(IllegalArgumentException("El nombre ya existe"))
        else Result.success(Unit)
    }
}

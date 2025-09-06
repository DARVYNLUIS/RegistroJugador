package edu.ucne.registrojugador.domain.jugador.usecase

import kotlinx.coroutines.flow.first
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository

class UpsertJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Int> {
        // Obtener todos los jugadores existentes para validar duplicados
        val existingJugadores = repository.observeJugadores()
            .first() // Obtener el valor actual de la lista de jugadores

        // Validar jugador
        val validationResult = validateJugadorUi(jugador.nombres, jugador.partidas.toString())
        if (!validationResult.isValid) {
            return Result.failure(IllegalArgumentException(validationResult.nombresError))
        }

        // Guardar jugador usando el repositorio
        return runCatching { repository.upsert(jugador) }
    }
}

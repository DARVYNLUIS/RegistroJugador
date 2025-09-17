package edu.ucne.registrojugador.domain.jugador.usecase.partida

import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExistePartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(jugador1Id: Int, jugador2Id: Int, fecha: String): Boolean {
        val partidas = repository.getPartidas().first() // Get the list directly

        return partidas.any {
            it.jugador1Id == jugador1Id &&
                    it.jugador2Id == jugador2Id &&
                    it.fecha == fecha
        }
    }
}
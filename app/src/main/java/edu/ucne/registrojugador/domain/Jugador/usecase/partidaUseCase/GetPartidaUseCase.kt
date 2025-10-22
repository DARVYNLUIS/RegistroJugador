package edu.ucne.registrojugador.domain.jugador.usecases

import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import javax.inject.Inject

class GetPartidasUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(): List<Partida> {
        return repository.getPartidasFromApi()
    }
}

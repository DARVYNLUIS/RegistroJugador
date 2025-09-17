package edu.ucne.registrojugador.domain.jugador.usecase.partida

import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import javax.inject.Inject

class ObservePartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    operator fun invoke() = repository.getPartidas()
}
package edu.ucne.registrojugador.domain.jugador.usecase.partidaUseCase


import edu.ucne.registrojugador.domain.jugador.repository.GameRepository
import edu.ucne.registrojugador.domain.jugador.model.Movimiento
import javax.inject.Inject

class GetMovimientosUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(partidaId: Int): List<Movimiento> {
        return repository.getMovimientos(partidaId)
    }
}

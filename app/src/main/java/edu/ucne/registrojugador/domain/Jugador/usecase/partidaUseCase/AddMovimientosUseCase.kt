package edu.ucne.registrojugador.domain.jugador.usecase.partidaUseCase


import edu.ucne.registrojugador.domain.jugador.repository.GameRepository
import edu.ucne.registrojugador.data.local.dto.Movimiento
import javax.inject.Inject

class AddMovimientoUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(movimiento: Movimiento): Boolean {
        return repository.addMovimiento(movimiento)
    }
}

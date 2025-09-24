package edu.ucne.registrojugador.domain.logro.usecase

import edu.ucne.registrojugador.domain.jugador.model.Logro
import edu.ucne.registrojugador.domain.logro.repository.LogroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLogrosUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    operator fun invoke(): Flow<List<Logro>> = repository.observeLogros()
}
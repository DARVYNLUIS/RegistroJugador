package edu.ucne.registrojugador.domain.logro.usecase

import edu.ucne.registrojugador.domain.jugador.model.Logro
import edu.ucne.registrojugador.domain.logro.repository.LogroRepository
import javax.inject.Inject

class UpsertLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(logro: Logro): Int {
        return repository.upsert(logro)
    }
}
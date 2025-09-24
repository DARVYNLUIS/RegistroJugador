package edu.ucne.registrojugador.domain.logro.usecase

import edu.ucne.registrojugador.domain.logro.repository.LogroRepository
import javax.inject.Inject

class DeleteLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
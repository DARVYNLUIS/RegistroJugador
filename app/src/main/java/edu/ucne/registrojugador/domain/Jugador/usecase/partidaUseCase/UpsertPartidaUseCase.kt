package edu.ucne.registrojugador.domain.jugador.usecase.partida

import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import javax.inject.Inject

class UpsertPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(partida: Partida) {
        if (partida.partidaId == 0) {
            repository.insertar(partida)
        } else {
            repository.actualizar(partida)
        }
    }
}
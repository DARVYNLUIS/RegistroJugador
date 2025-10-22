package edu.ucne.registrojugador.domain.jugador.usecase.partidaUseCase


import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import javax.inject.Inject

class PostPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(jugador1Id: Int, jugador2Id: Int): Partida {
        return repository.crearPartidaApi(jugador1Id, jugador2Id)
    }
}

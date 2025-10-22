package edu.ucne.registrojugador.data.repository

import edu.ucne.registrojugador.data.local.dto.Movimiento
import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.repository.GameRepository
import edu.ucne.registrojugador.data.api.RetrofitClient
import edu.ucne.registrojugador.data.local.dto.PartidaDto
import javax.inject.Inject
import retrofit2.Response


class GameRepositoryImpl @Inject constructor() : GameRepository {

    override suspend fun getPartidas(): List<Partida> {
        val dtos: List<PartidaDto> = RetrofitClient.apiService.getPartidas()
        return dtos.map { dto ->
            Partida(
                partidaId = dto.partidaId,
                jugador1Id = dto.jugador1Id,
                jugador2Id = dto.jugador2Id,
                fecha = "",
                ganadorId = null,
                esFinalizada = false
            )
        }
    }

    override suspend fun addPartida(partida: Partida): Boolean {
        val dto = PartidaDto(
            partidaId = partida.partidaId,
            jugador1Id = partida.jugador1Id,
            jugador2Id = partida.jugador2Id
        )
        val response = RetrofitClient.apiService.postPartida(dto)
        return response.isSuccessful
    }

    override suspend fun getMovimientos(partidaId: Int): List<Movimiento> {
        return RetrofitClient.apiService.getMovimientos(partidaId)
    }

    override suspend fun addMovimiento(movimiento: Movimiento): Boolean {
        val response = RetrofitClient.apiService.postMovimiento(movimiento)
        return response.isSuccessful
    }
}

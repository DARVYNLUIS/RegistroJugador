package edu.ucne.registrojugador.data.repository

import edu.ucne.registrojugador.data.api.ApiService
import edu.ucne.registrojugador.data.api.RetrofitClient
import edu.ucne.registrojugador.data.local.mapper.toDomain
import edu.ucne.registrojugador.data.local.mapper.toDto
import edu.ucne.registrojugador.domain.jugador.model.Player
import edu.ucne.registrojugador.domain.jugador.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PlayerRepository {

    override suspend fun getPlayers(): List<Player> {
        return apiService.getPlayers().map { it.toDomain() }
    }

    override suspend fun addPlayer(player: Player): Boolean {
        val response = apiService.postPlayer(player.toDto())
        return response.isSuccessful
    }
}
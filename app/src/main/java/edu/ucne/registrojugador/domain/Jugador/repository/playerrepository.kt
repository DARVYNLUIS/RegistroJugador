package edu.ucne.registrojugador.domain.jugador.repository



import edu.ucne.registrojugador.domain.jugador.model.Player

interface PlayerRepository {
    suspend fun getPlayers(): List<Player>
    suspend fun addPlayer(player: Player): Boolean
}
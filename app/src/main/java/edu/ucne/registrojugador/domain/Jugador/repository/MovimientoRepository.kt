package edu.ucne.registrojugador.domain.jugador.repository



import edu.ucne.registrojugador.data.local.dto.Movimiento
import edu.ucne.registrojugador.domain.jugador.model.Partida


interface GameRepository {
    suspend fun getPartidas(): List<Partida>
    suspend fun addPartida(partida: Partida): Boolean

    suspend fun getMovimientos(partidaId: Int): List<Movimiento>
    suspend fun addMovimiento(movimiento: Movimiento): Boolean
}

package edu.ucne.registrojugador.domain.jugador.repository

import edu.ucne.registrojugador.domain.jugador.model.Partida
import kotlinx.coroutines.flow.Flow

interface PartidaRepository {
    // --- Room ---
    fun getPartidas(): Flow<List<Partida>>
    suspend fun getPartidaById(id: Int): Partida?
    suspend fun insertar(partida: Partida)
    suspend fun actualizar(partida: Partida)
    suspend fun eliminar(partida: Partida)
    suspend fun crearPartida(jugador1Id: Int, jugador2Id: Int): Partida

    // --- API ---
    suspend fun getPartidasFromApi(): List<Partida>
    suspend fun crearPartidaApi(jugador1Id: Int, jugador2Id: Int): Partida
}

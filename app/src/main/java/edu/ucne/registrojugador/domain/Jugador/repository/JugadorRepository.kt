package edu.ucne.registrojugador.domain.jugador.repository

import edu.ucne.registrojugador.domain.jugador.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observeJugadores(): Flow<List<Jugador>>
    suspend fun getJugador(id: Int): Jugador?
    suspend fun upsert(jugador: Jugador): Int
    suspend fun delete(id: Int)
    suspend fun existePorNombre(nombre: String): Boolean
}

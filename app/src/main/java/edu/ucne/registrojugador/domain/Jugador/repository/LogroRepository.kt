package edu.ucne.registrojugador.domain.logro.repository

import edu.ucne.registrojugador.domain.jugador.model.Logro
import kotlinx.coroutines.flow.Flow


interface LogroRepository {



    fun observeLogros(): Flow<List<Logro>>
    suspend fun getLogro(id: Int): Logro?
    suspend fun upsert(logro: Logro): Int
    suspend fun delete(id: Int)
}
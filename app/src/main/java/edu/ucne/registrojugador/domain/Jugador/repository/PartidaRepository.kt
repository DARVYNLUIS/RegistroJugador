// file: edu/ucne/registrojugador/domain/jugador/repository/PartidaRepository.kt

package edu.ucne.registrojugador.domain.jugador.repository

import edu.ucne.registrojugador.domain.jugador.model.Partida
import kotlinx.coroutines.flow.Flow

interface PartidaRepository {
    fun getPartidas(): Flow<List<Partida>>
    suspend fun getPartidaById(id: Int): Partida?
    suspend fun insertar(partida: Partida)
    suspend fun actualizar(partida: Partida)
    suspend fun eliminar(partida: Partida) // Corrected: This should be a domain model
}
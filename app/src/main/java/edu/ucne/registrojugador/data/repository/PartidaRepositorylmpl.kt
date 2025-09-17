// In file: PartidaRepositoryImpl.kt

package edu.ucne.registrojugador.data.repository

import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.mapper.toDomain
import edu.ucne.registrojugador.data.local.mapper.toEntity
import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartidaRepositoryImpl @Inject constructor(
    private val dao: PartidaDao
) : PartidaRepository {

    override fun getPartidas(): Flow<List<Partida>> = dao.getPartidas().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun getPartidaById(id: Int): Partida? = dao.getPartidaById(id)?.toDomain()

    override suspend fun insertar(partida: Partida) = dao.insertar(partida.toEntity())

    override suspend fun actualizar(partida: Partida) = dao.actualizar(partida.toEntity())

    override suspend fun eliminar(partida: Partida) = dao.eliminar(partida.toEntity())
}
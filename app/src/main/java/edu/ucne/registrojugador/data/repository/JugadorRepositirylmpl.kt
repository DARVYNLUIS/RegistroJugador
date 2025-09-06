package edu.ucne.registrojugador.data.repository

import edu.ucne.registrojugador.data.local.dao.JugadorDao
import edu.ucne.registrojugador.data.local.mapper.toDomain
import edu.ucne.registrojugador.data.local.mapper.toEntity
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject



class JugadorRepositoryImpl@Inject constructor(
    private val dao: JugadorDao
) : JugadorRepository {

    override fun observeJugadores(): Flow<List<Jugador>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getJugador(id: Int): Jugador? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(jugador: Jugador): Int {
        dao.upsert(jugador.toEntity())
        return jugador.jugadorId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun existePorNombre(nombre: String): Boolean = dao.existePorNombre(nombre)

}


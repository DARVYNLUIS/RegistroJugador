package edu.ucne.registrojugador.data.repository


import edu.ucne.registrojugador.data.logro.LogroDao
import edu.ucne.registrojugador.domain.jugador.model.Logro
import edu.ucne.registrojugador.domain.logro.repository.LogroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toDomain
import toEntity
import javax.inject.Inject

class LogroRepositoryImpl @Inject constructor(
    private val dao: LogroDao
) : LogroRepository {

    override fun observeLogros(): Flow<List<Logro>> = dao.getAllLogros().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getLogro(id: Int): Logro? =
        dao.getLogroById(id)?.toDomain()

    override suspend fun upsert(logro: Logro): Int {
        dao.insertLogro(logro.toEntity())
        return logro.logroId
    }

    override suspend fun delete(id: Int) {
        dao.deleteLogro(dao.getLogroById(id)!!)
    }
}
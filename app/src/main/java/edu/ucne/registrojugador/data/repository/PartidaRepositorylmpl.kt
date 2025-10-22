package edu.ucne.registrojugador.data.repository

import edu.ucne.registrojugador.data.api.ApiService
import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.dto.PartidaDto
import edu.ucne.registrojugador.data.local.mapper.PartidaMapper
import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class PartidaRepositoryImpl @Inject constructor(
    private val dao: PartidaDao,
    private val api: ApiService
) : PartidaRepository {

    override fun getPartidas(): Flow<List<Partida>> =
        dao.getPartidas().map { entities -> PartidaMapper.toDomainList(entities) }

    override suspend fun getPartidaById(id: Int): Partida? =
        dao.getPartidaById(id)?.let { PartidaMapper.toDomain(it) }

    override suspend fun insertar(partida: Partida) =
        dao.insertar(PartidaMapper.toEntity(partida))

    override suspend fun actualizar(partida: Partida) =
        dao.actualizar(PartidaMapper.toEntity(partida))

    override suspend fun eliminar(partida: Partida) =
        dao.eliminar(PartidaMapper.toEntity(partida))

    override suspend fun crearPartida(jugador1Id: Int, jugador2Id: Int): Partida {
        val nuevaPartida = Partida(
            fecha = java.time.LocalDate.now().toString(),
            jugador1Id = jugador1Id,
            jugador2Id = jugador2Id,
            ganadorId = null,
            esFinalizada = false
        )
        dao.insertar(PartidaMapper.toEntity(nuevaPartida))
        return nuevaPartida
    }

    override suspend fun getPartidasFromApi(): List<Partida> {
        val dtos: List<PartidaDto> = api.getPartidas() // API devuelve solo los tres campos
        val partidas = dtos.map { PartidaMapper.toDomain(it) }

        partidas.forEach { dao.insertar(PartidaMapper.toEntity(it)) }
        return partidas
    }

    override suspend fun crearPartidaApi(jugador1Id: Int, jugador2Id: Int): Partida {
        val request = PartidaDto(jugador1Id = jugador1Id, jugador2Id = jugador2Id)
        val response: Response<Unit> = api.postPartida(request)

        if (!response.isSuccessful) {
            throw Exception("Error al crear partida en API: ${response.code()}")
        }

        val nuevaPartida = Partida(
            partidaId = 0,
            fecha = java.time.LocalDate.now().toString(),
            jugador1Id = jugador1Id,
            jugador2Id = jugador2Id,
            ganadorId = null,
            esFinalizada = false
        )

        dao.insertar(PartidaMapper.toEntity(nuevaPartida))
        return nuevaPartida
    }
}

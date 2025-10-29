package edu.ucne.registrojugador.data.repository

import edu.ucne.registrojugador.data.api.RetrofitClient
import edu.ucne.registrojugador.data.local.dao.MovimientoDao
import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.dto.MovimientoDto
import edu.ucne.registrojugador.data.local.dto.PartidaDto
import edu.ucne.registrojugador.data.local.entities.MovimientoEntity
import edu.ucne.registrojugador.data.local.entities.PartidaEntity
import edu.ucne.registrojugador.domain.jugador.model.Movimiento
import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.domain.jugador.repository.GameRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val partidaDao: PartidaDao,
    private val movimientoDao: MovimientoDao
) : GameRepository {

    override suspend fun getPartidas(): List<Partida> {
        val local = partidaDao.getAllPartidas().map { entity ->
            Partida(
                partidaId = entity.partidaId,
                jugador1Id = entity.jugador1Id,
                jugador2Id = entity.jugador2Id,
                fecha = entity.fecha,
                ganadorId = entity.ganadorId,
                esFinalizada = entity.esFinalizada
            )
        }

        return if (local.isNotEmpty()) local
        else {
            val dtos: List<PartidaDto> = RetrofitClient.apiService.getPartidas()
            dtos.map { dto ->
                Partida(
                    partidaId = dto.partidaId,
                    jugador1Id = dto.jugador1Id,
                    jugador2Id = dto.jugador2Id,
                    fecha = "",
                    ganadorId = null,
                    esFinalizada = false
                )
            }
        }
    }

    override suspend fun addPartida(partida: Partida): Boolean {
        partidaDao.insertar(
            PartidaEntity(
                partidaId = partida.partidaId,
                jugador1Id = partida.jugador1Id,
                jugador2Id = partida.jugador2Id,
                fecha = partida.fecha,
                ganadorId = partida.ganadorId,
                esFinalizada = partida.esFinalizada
            )
        )

        val dto = PartidaDto(
            partidaId = partida.partidaId,
            jugador1Id = partida.jugador1Id,
            jugador2Id = partida.jugador2Id
        )
        val response = RetrofitClient.apiService.postPartida(dto)
        return response.isSuccessful
    }

    override suspend fun getMovimientos(partidaId: Int): List<Movimiento> {
        val localEntities = movimientoDao.getMovimientosByPartida(partidaId).first()
        return localEntities.map { entity ->
            Movimiento(
                partidaId = entity.partidaId,
                jugador = entity.jugador,
                posicionFila = entity.fila,
                posicionColumna = entity.columna
            )
        }
    }

    override suspend fun addMovimiento(movimiento: Movimiento): Boolean {
        movimientoDao.insertMovimiento(
            MovimientoEntity(
                partidaId = movimiento.partidaId,
                jugador = movimiento.jugador,
                fila = movimiento.posicionFila,
                columna = movimiento.posicionColumna
            )
        )

        val dto = MovimientoDto(
            partidaId = movimiento.partidaId,
            jugador = movimiento.jugador,
            posicionFila = movimiento.posicionFila,
            posicionColumna = movimiento.posicionColumna
        )

        val response = RetrofitClient.apiService.postMovimiento(dto)
        return response.isSuccessful
    }
    suspend fun getMovimientosPendientes(): List<Movimiento> {
        return movimientoDao.getMovimientosPendientes().map { entity ->
            Movimiento(
                partidaId = entity.partidaId,
                jugador = entity.jugador,
                posicionFila = entity.fila,
                posicionColumna = entity.columna
            )
        }
    }

    suspend fun addMovimientoOnline(movimiento: Movimiento): Boolean {
        val dto = MovimientoDto(
            partidaId = movimiento.partidaId,
            jugador = movimiento.jugador,
            posicionFila = movimiento.posicionFila,
            posicionColumna = movimiento.posicionColumna
        )
        val response = RetrofitClient.apiService.postMovimiento(dto)
        return response.isSuccessful
    }

    suspend fun marcarComoEnviado(movimiento: Movimiento) {

        val entity = MovimientoEntity(
            id = movimiento.id ?: 0,
            partidaId = movimiento.partidaId,
            jugador = movimiento.jugador,
            fila = movimiento.posicionFila,
            columna = movimiento.posicionColumna
        )
        movimientoDao.deleteMovimiento(entity)
    }
}
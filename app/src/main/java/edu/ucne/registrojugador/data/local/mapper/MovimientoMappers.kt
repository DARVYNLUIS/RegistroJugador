package edu.ucne.registrojugador.data.local.mapper

import edu.ucne.registrojugador.data.local.dto.MovimientoDto
import edu.ucne.registrojugador.domain.jugador.model.Movimiento

fun MovimientoDto.toDomain(): Movimiento = Movimiento(
    partidaId = partidaId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna
)

fun Movimiento.toDto(): MovimientoDto = MovimientoDto(
    partidaId = partidaId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna
)
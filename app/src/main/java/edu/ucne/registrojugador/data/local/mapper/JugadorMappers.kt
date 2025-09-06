package edu.ucne.registrojugador.data.local.mapper

import edu.ucne.registrojugador.data.local.entities.JugadorEntity
import edu.ucne.registrojugador.domain.jugador.model.Jugador

fun JugadorEntity.toDomain(): Jugador = Jugador(
    jugadorId = JugadorId,
    nombres = nombres,
    partidas = partidas
)

fun Jugador.toEntity(): JugadorEntity = JugadorEntity(
    JugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas
)

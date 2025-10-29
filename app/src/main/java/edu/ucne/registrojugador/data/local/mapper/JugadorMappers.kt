package edu.ucne.registrojugador.data.local.mapper

import edu.ucne.registrojugador.data.local.entities.JugadorEntity
import edu.ucne.registrojugador.domain.jugador.model.Jugador


fun JugadorEntity.toDomain() = Jugador(
    JugadorId = JugadorId,
    nombres = nombres,
    partidas = emptyList()
)

fun Jugador.toEntity() = JugadorEntity(
    JugadorId = JugadorId,
    nombres = nombres
)
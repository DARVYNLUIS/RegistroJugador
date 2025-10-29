package edu.ucne.registrojugador.data.local.mapper

import edu.ucne.registrojugador.data.local.dto.PlayerDto
import edu.ucne.registrojugador.domain.jugador.model.Player


fun PlayerDto.toDomain(): Player {
    return Player(
        playerId = this.jugadorId,
        nombres = this.nombres,
        email = this.email
    )
}

fun Player.toDto(): PlayerDto {
    return PlayerDto(
        jugadorId = this.playerId,
        nombres = this.nombres,
        email = this.email
    )
}
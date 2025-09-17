package edu.ucne.registrojugador.data.local.mapper


import edu.ucne.registrojugador.data.local.entities.PartidaEntity
import edu.ucne.registrojugador.domain.jugador.model.Partida

// Entity → Domain
fun PartidaEntity.toDomain(): Partida {
    return Partida(
        partidaId = this.partidaId,
        fecha = this.fecha,
        jugador1Id = this.jugador1Id,
        jugador2Id = this.jugador2Id,
        ganadorId = this.ganadorId,
        esFinalizada = this.esFinalizada
    )
}

// Domain → Entity
fun Partida.toEntity(): PartidaEntity {
    return PartidaEntity(
        partidaId = this.partidaId,
        fecha = this.fecha,
        jugador1Id = this.jugador1Id,
        jugador2Id = this.jugador2Id,
        ganadorId = this.ganadorId,
        esFinalizada = this.esFinalizada
    )
}

// Lista de Entity → Lista de Domain
fun List<PartidaEntity>.toDomainList(): List<Partida> {
    return this.map { it.toDomain() }
}

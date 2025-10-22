package edu.ucne.registrojugador.data.local.mapper

import edu.ucne.registrojugador.data.local.dto.PartidaDto
import edu.ucne.registrojugador.data.local.entities.PartidaEntity
import edu.ucne.registrojugador.domain.jugador.model.Partida

object PartidaMapper {

    // --- Entity → Domain ---
    fun toDomain(entity: PartidaEntity): Partida {
        return Partida(
            partidaId = entity.partidaId,
            fecha = entity.fecha,
            jugador1Id = entity.jugador1Id,
            jugador2Id = entity.jugador2Id,
            ganadorId = entity.ganadorId,
            esFinalizada = entity.esFinalizada
        )
    }

    // --- Domain → Entity ---
    fun toEntity(domain: Partida): PartidaEntity {
        return PartidaEntity(
            partidaId = domain.partidaId,
            fecha = domain.fecha,
            jugador1Id = domain.jugador1Id,
            jugador2Id = domain.jugador2Id,
            ganadorId = domain.ganadorId,
            esFinalizada = domain.esFinalizada
        )
    }

    // --- DTO → Domain ---
    fun toDomain(dto: PartidaDto): Partida {
        return Partida(
            partidaId = dto.partidaId,
            jugador1Id = dto.jugador1Id,
            jugador2Id = dto.jugador2Id,
            fecha = java.time.LocalDate.now().toString(), // se asigna fecha actual
            ganadorId = null, // inicialmente sin ganador
            esFinalizada = false // inicialmente no finalizada
        )
    }

    // --- Domain → DTO ---
    fun toDto(domain: Partida): PartidaDto {
        return PartidaDto(
            partidaId = domain.partidaId,
            jugador1Id = domain.jugador1Id,
            jugador2Id = domain.jugador2Id
        )
    }

    // --- Lista de Entity → Lista de Domain ---
    fun toDomainList(entities: List<PartidaEntity>): List<Partida> {
        return entities.map { toDomain(it) }
    }

    // --- Lista de DTO → Lista de Domain ---
    fun toDomainListFromDto(dtos: List<PartidaDto>): List<Partida> {
        return dtos.map { toDomain(it) }
    }
}

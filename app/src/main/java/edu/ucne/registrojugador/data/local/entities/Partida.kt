package edu.ucne.registrojugador.data.local.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class PartidaEntity(
    @PrimaryKey(autoGenerate = true)
    val partidaId: Int = 0,
    val jugador1Id: Int,
    val jugador2Id: Int,
    val fecha: String?,
    val ganadorId: Int? = null,
    val esFinalizada: Boolean = false
)
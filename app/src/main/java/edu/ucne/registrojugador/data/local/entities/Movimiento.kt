package edu.ucne.registrojugador.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos")
data class MovimientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val partidaId: Int,
    val jugador: String,
    val fila: Int,
    val columna: Int
)
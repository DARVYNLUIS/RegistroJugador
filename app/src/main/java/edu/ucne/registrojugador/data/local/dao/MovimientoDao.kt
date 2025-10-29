package edu.ucne.registrojugador.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import edu.ucne.registrojugador.data.local.entities.MovimientoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {

    @Query("SELECT * FROM movimientos WHERE partidaId = :partidaId")
    fun getMovimientosByPartida(partidaId: Int): Flow<List<MovimientoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovimiento(movimiento: MovimientoEntity)

    @Delete
    suspend fun deleteMovimiento(movimiento: MovimientoEntity)

    @Query("SELECT * FROM movimientos")
    suspend fun getMovimientosPendientes(): List<MovimientoEntity>
}
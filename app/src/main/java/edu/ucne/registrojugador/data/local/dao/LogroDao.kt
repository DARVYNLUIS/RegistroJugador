package edu.ucne.registrojugador.data.logro

import androidx.room.*
import edu.ucne.registrojugador.data.local.entities.LogroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDao {

    @Query("SELECT * FROM Logros ORDER BY logroId DESC")
    fun getAllLogros(): Flow<List<LogroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogro(logro: LogroEntity)

    @Delete
    suspend fun deleteLogro(logro: LogroEntity)

    @Query("SELECT * FROM Logros WHERE logroId = :id")
    suspend fun getLogroById(id: Int): LogroEntity?
}

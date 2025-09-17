package edu.ucne.registrojugador.data.local.dao

import androidx.room.*
import edu.ucne.registrojugador.data.local.entities.PartidaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(partida: PartidaEntity)

    @Update
    suspend fun actualizar(partida: PartidaEntity)

    @Delete
    suspend fun eliminar(partida: PartidaEntity)

    @Query("SELECT * FROM partidas ORDER BY fecha DESC")
    fun getPartidas(): Flow<List<PartidaEntity>>

    @Query("SELECT * FROM partidas WHERE partidaId = :id LIMIT 1")
    suspend fun getPartidaById(id: Int): PartidaEntity?
}

package edu.ucne.registrojugador.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.ucne.registrojugador.data.local.dao.JugadorDao
import edu.ucne.registrojugador.data.local.dao.MovimientoDao
import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.entities.JugadorEntity
import edu.ucne.registrojugador.data.local.entities.MovimientoEntity
import edu.ucne.registrojugador.data.local.entities.PartidaEntity

@Database(
    entities = [JugadorEntity::class, PartidaEntity::class, MovimientoEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
    abstract fun partidaDao(): PartidaDao
    abstract fun movimientoDao(): MovimientoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
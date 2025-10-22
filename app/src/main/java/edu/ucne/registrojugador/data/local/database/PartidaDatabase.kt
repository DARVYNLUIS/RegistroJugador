package edu.ucne.registrojugador.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import android.content.Context
import edu.ucne.registrojugador.data.local.dao.JugadorDao
import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.entities.JugadorEntity
import edu.ucne.registrojugador.data.local.entities.PartidaEntity

@Database(
    entities = [JugadorEntity::class, PartidaEntity::class],
    version = 2, // <- Incrementa la versión
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
    abstract fun partidaDao(): PartidaDao

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
                    .fallbackToDestructiveMigration() // <- Borra la DB vieja si hay conflicto de versión
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

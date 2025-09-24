package edu.ucne.registrojugador.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrojugador.data.local.entities.LogroEntity
import edu.ucne.registrojugador.data.logro.LogroDao


@Database(
    entities = [LogroEntity::class],
    version = 1,
    exportSchema = false
)

abstract class LogroDatabase : RoomDatabase(){
    abstract fun logroDao(): LogroDao
}
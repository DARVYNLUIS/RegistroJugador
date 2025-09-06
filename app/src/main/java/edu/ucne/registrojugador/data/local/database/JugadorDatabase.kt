package edu.ucne.registrojugador.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrojugador.data.local.entities.JugadorEntity
import edu.ucne.registrojugador.data.local.dao.JugadorDao

@Database(
    entities = [
        JugadorEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract  class JugadorDatabase : RoomDatabase(){
    abstract fun JugadorDao(): JugadorDao
}
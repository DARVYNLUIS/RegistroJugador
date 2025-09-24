package edu.ucne.registrojugador.data.local.database

import edu.ucne.registrojugador.data.logro.LogroDao



import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugador.data.local.dao.JugadorDao
import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.database.JugadorDatabase
import edu.ucne.registrojugador.data.local.database.LogroDatabase
import edu.ucne.registrojugador.data.local.database.AppDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    // Provee la base de datos de Jugador
    @Provides
    @Singleton
    fun provideJugadorDatabase(@ApplicationContext appContext: Context): JugadorDatabase {
        return Room.databaseBuilder(
            appContext,
            JugadorDatabase::class.java,
            "Jugador.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Provee la base de datos de Partida
    @Provides
    @Singleton
    fun providePartidaDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Partida.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Provee la base de datos de Logro
    @Provides
    @Singleton
    fun provideLogroDatabase(@ApplicationContext appContext: Context): LogroDatabase {
        return Room.databaseBuilder(
            appContext,
            LogroDatabase::class.java,
            "Logro.db"
        ).build()
    }

    // Provee los DAOs
    @Provides
    @Singleton
    fun provideJugadorDao(db: AppDatabase): JugadorDao = db.jugadorDao()

    @Provides
    @Singleton
    fun providePartidaDao(db: AppDatabase): PartidaDao = db.partidaDao()

    @Provides
    @Singleton
    fun provideLogroDao(db: LogroDatabase): LogroDao = db.logroDao()
}
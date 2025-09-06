package edu.ucne.registrojugador.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugador.data.local.dao.JugadorDao
import edu.ucne.registrojugador.data.local.database.JugadorDatabase
import edu.ucne.registrojugador.data.repository.JugadorRepositoryImpl
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import edu.ucne.registrojugador.domain.jugador.usecase.DeleteJugadorUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.GetJugadorUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.ObserveJugadorUseCase
import edu.ucne.registrojugador.domain.jugador.usecase.UpsertJugadorUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideJugadorDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            JugadorDatabase::class.java,
            "Jugadores.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideJugadorDao(jugadorDatabase: JugadorDatabase ) = jugadorDatabase.JugadorDao()

    @Provides
    @Singleton
    fun provideGetJugadorUseCase(jugadorRepository: JugadorRepository) : GetJugadorUseCase{
        return GetJugadorUseCase(jugadorRepository)
    }
    @Provides
    @Singleton
    fun provideObserveJugadorUseCase(jugadorRepository: JugadorRepository) : ObserveJugadorUseCase{
        return ObserveJugadorUseCase(jugadorRepository)
    }
    @Provides
    @Singleton
    fun provideUpsertJugadorUseCase(jugadorRepository: JugadorRepository) : UpsertJugadorUseCase{
        return UpsertJugadorUseCase(jugadorRepository)
    }
    @Provides
    @Singleton
    fun provideDeleteJugadorUseCase(jugadorRepository: JugadorRepository) : DeleteJugadorUseCase{
        return DeleteJugadorUseCase(jugadorRepository)
    }

    @Provides
    @Singleton
    fun provideJugadorRepository(jugadorDao: JugadorDao): JugadorRepository{
        return JugadorRepositoryImpl(jugadorDao)
    }
}


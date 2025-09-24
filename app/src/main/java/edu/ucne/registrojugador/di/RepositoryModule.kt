// file: edu/ucne/registrojugador/di/RepositoryModule.kt
package edu.ucne.registrojugador.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugador.data.repository.JugadorRepositoryImpl
import edu.ucne.registrojugador.data.repository.PartidaRepositoryImpl
import edu.ucne.registrojugador.data.repository.LogroRepositoryImpl
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import edu.ucne.registrojugador.domain.logro.repository.LogroRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindJugadorRepository(
        jugadorRepositoryImpl: JugadorRepositoryImpl
    ): JugadorRepository

    @Binds
    @Singleton
    abstract fun bindPartidaRepository(
        partidaRepositoryImpl: PartidaRepositoryImpl
    ): PartidaRepository

    @Binds
    @Singleton
    abstract fun bindLogroRepository(
        logroRepositoryImpl: LogroRepositoryImpl
    ): LogroRepository
}
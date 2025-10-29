package edu.ucne.registrojugador.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugador.data.repository.GameRepositoryImpl
import edu.ucne.registrojugador.data.repository.JugadorRepositoryImpl
import edu.ucne.registrojugador.data.repository.PartidaRepositoryImpl
import edu.ucne.registrojugador.data.repository.PlayerRepositoryImpl
import edu.ucne.registrojugador.domain.jugador.repository.GameRepository
import edu.ucne.registrojugador.domain.jugador.repository.JugadorRepository
import edu.ucne.registrojugador.domain.jugador.repository.PartidaRepository
import edu.ucne.registrojugador.domain.jugador.repository.PlayerRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGameRepository(
        impl: GameRepositoryImpl
    ): GameRepository

    @Binds
    abstract fun bindJugadorRepository(
        impl: JugadorRepositoryImpl
    ): JugadorRepository
    @Binds
    abstract fun bindPlayerRepository(
        impl: PlayerRepositoryImpl
    ): PlayerRepository

    @Binds
    abstract fun bindPartidaRepository(
        impl: PartidaRepositoryImpl
    ): PartidaRepository
}

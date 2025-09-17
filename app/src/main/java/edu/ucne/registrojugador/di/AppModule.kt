// file: C:/APLICADA_2/app/src/main/java/edu/ucne/registrojugador/di/AppModule.kt

package edu.ucne.registrojugador.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugador.data.local.dao.JugadorDao
import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.database.AppDatabase
import edu.ucne.registrojugador.domain.jugador.usecase.*
import edu.ucne.registrojugador.domain.jugador.usecase.partida.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [RepositoryModule::class]) // Include the new module here
object AppModule {

    // Base de datos
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RegistroJugador.db"
        ).fallbackToDestructiveMigration()
            .build()

    // DAOs
    @Provides
    @Singleton
    fun provideJugadorDao(db: AppDatabase): JugadorDao = db.jugadorDao()

    @Provides
    @Singleton
    fun providePartidaDao(db: AppDatabase): PartidaDao = db.partidaDao()

    // UseCases Jugador - Hilt can create these automatically if they have @Inject constructors.
    // So you don't need to manually provide them here.

    // UseCases Partida - Same here, Hilt can create these.
}
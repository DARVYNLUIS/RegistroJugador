package edu.ucne.registrojugador.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.appdistribution.gradle.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugador.data.local.dao.JugadorDao
import edu.ucne.registrojugador.data.local.dao.PartidaDao
import edu.ucne.registrojugador.data.local.database.AppDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [RepositoryModule::class])
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RegistroJugador.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideJugadorDao(db: AppDatabase): JugadorDao = db.jugadorDao()

    @Provides
    @Singleton
    fun providePartidaDao(db: AppDatabase): PartidaDao = db.partidaDao()



    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}

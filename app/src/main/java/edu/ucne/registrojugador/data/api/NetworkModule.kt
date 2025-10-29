package edu.ucne.registrojugador.data.api


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugador.data.api.ApiService
import javax.inject.Singleton
import edu.ucne.registrojugador.data.api.RetrofitClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = RetrofitClient.apiService
}

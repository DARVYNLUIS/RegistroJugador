package edu.ucne.registrojugador.data.api


import edu.ucne.registrojugador.data.local.dto.Movimiento
import edu.ucne.registrojugador.data.local.dto.PartidaDto
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("partidas")
    suspend fun getPartidas(): List<PartidaDto>

    @POST("partidas")
    suspend fun postPartida(@Body partida: PartidaDto): retrofit2.Response<Unit>

    @GET("Movimientos/{partidaId}")
    suspend fun getMovimientos(@Path("partidaId") partidaId: Int): List<Movimiento>

    @POST("Movimientos")
    suspend fun postMovimiento(@Body movimiento: Movimiento): retrofit2.Response<Unit>
}

package edu.ucne.registrojugador.workers


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.ucne.registrojugador.data.repository.GameRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: GameRepositoryImpl
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val pendientes = repository.getMovimientosPendientes()
            pendientes.forEach { movimiento ->
                val success = repository.addMovimientoOnline(movimiento)
                if (success) repository.marcarComoEnviado(movimiento)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
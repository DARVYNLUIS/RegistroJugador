package edu.ucne.registrojugador.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkerScheduler {

    fun scheduleSyncWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_movimientos",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
package edu.ucne.registrojugador

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import edu.ucne.registrojugador.workers.WorkerScheduler

@HiltAndroidApp
class RegistroJugadorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        WorkerScheduler.scheduleSyncWorker(this)
    }
}
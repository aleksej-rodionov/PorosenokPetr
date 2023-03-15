package space.rodionov.porosenokpetr.core

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import space.rodionov.porosenokpetr.core.di.DaggerMainComponent
import space.rodionov.porosenokpetr.core.di.MainComponent
import space.rodionov.porosenokpetr.feature_driller.work.NotifyWorkerFactory
import javax.inject.Inject

//todo :app module

class PorosenokPetrApp: Application() {

    @Inject lateinit var workerFactory: NotifyWorkerFactory

    override fun onCreate() {
        super.onCreate() // todo move creating notificationChannel and notificationManager here
        component = DaggerMainComponent.builder().application(this).build()
        component?.inject(this)

        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, workManagerConfig)
    }

    companion object {
        var component: MainComponent? = null
    }
}
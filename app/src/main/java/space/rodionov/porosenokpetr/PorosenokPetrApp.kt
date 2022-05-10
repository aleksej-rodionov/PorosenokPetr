package space.rodionov.porosenokpetr

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import space.rodionov.porosenokpetr.feature_driller.di.MainComponent
import javax.inject.Inject


class PorosenokPetrApp: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    override fun onCreate() {
        super.onCreate() // todo move creating notificationChannel and notificationManager here
        component = DaggerMainComponent.builder().application(this).build()
        component?.inject(this)
    }

    companion object {
        var component: MainComponent? = null
    }
}
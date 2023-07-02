package space.rodionov.porosenokpetr.main

import android.app.Application
import space.rodionov.porosenokpetr.main.di.AppComponent
import space.rodionov.porosenokpetr.main.di.DaggerAppComponent

class PorosenokPetrApp: Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().application(this).build()
        component?.inject(this)
    }

    companion object {
        var component: AppComponent? = null
    }
}
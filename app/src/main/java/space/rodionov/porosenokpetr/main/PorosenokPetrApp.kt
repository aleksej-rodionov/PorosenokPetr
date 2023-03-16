package space.rodionov.porosenokpetr.main

import android.app.Application
import space.rodionov.porosenokpetr.main.di.DaggerMainComponent
import space.rodionov.porosenokpetr.main.di.MainComponent

class PorosenokPetrApp: Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerMainComponent.builder().application(this).build()
        component?.inject(this)
    }

    companion object {
        var component: MainComponent? = null
    }
}
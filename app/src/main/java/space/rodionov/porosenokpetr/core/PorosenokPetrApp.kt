package space.rodionov.porosenokpetr.core

import android.app.Application
import space.rodionov.porosenokpetr.core.di.DaggerMainComponent
import space.rodionov.porosenokpetr.core.di.MainComponent

//todo :app module

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
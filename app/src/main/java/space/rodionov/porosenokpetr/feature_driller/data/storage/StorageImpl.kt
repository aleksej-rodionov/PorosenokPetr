package space.rodionov.porosenokpetr.feature_driller.data.storage

import android.app.Application
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import space.rodionov.porosenokpetr.Constants
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class StorageImpl /*@Inject constructor*/(
    /*@ApplicationContext*/ app: Application
): Storage {

    private val sharedPref =
        app.getSharedPreferences(Constants.SHARED_PREF_STORE, Context.MODE_PRIVATE)

    override fun setMode(isNight: Boolean) {
        sharedPref.edit().putBoolean(Constants.MODE, isNight).apply()
    }
    override fun getMode(): Boolean {
        return sharedPref.getBoolean(Constants.MODE, Constants.DEFAULT_MODE)
    }
}
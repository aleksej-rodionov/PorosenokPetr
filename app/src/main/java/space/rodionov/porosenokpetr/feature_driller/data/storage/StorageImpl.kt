package space.rodionov.porosenokpetr.feature_driller.data.storage

import android.app.Application
import android.content.Context
import android.util.Log
import space.rodionov.porosenokpetr.Constants
import space.rodionov.porosenokpetr.Constants.TAG_PETR

//@Singleton
class StorageImpl /*@Inject constructor*/(
    /*@ApplicationContext*/ app: Application
): Storage {

    private val sharedPref =
        app.getSharedPreferences(Constants.SHARED_PREF_STORE, Context.MODE_PRIVATE)

    // night mode====================================
    override fun getMode(): Int {
        return sharedPref.getInt(Constants.MODE, Constants.MODE_LIGHT)
    }
    override fun setMode(mode: Int) {
        sharedPref.edit().putInt(Constants.MODE, mode).apply()
    }

    // follow system mode================================
    override fun getFollowSystemMode(): Boolean {
        val follow = sharedPref.getBoolean(Constants.FOLLOW_SYSTEM_MODE, Constants.DEFAULT_FOLLOW_SYSTEM_MODE)
//        Log.d(TAG_PETR, "getFollowSystemMode: in storage = $follow")
        return follow
    }
    override fun setFollowSystemMode(follow: Boolean) {
        sharedPref.edit().putBoolean(Constants.FOLLOW_SYSTEM_MODE, follow).apply()
    }
}
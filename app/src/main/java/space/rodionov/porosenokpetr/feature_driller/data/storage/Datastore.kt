package space.rodionov.porosenokpetr.feature_driller.data.storage

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.feature_driller.utils.Constants.TAG_PETR
import java.io.IOException

private val Context.datastore by preferencesDataStore("datastore")

//@Singleton
class Datastore /*@Inject constructor*/(
   /* @ApplicationContext*/ app: Application
) {

    private val datastore = app.datastore

    private object PrefKeys {
        val CATEGORY = stringPreferencesKey("catName")
        val TRANSLATION_DIRECTION = booleanPreferencesKey("transDir")
        val MODE = intPreferencesKey("mode")
        val FOLLOW_SYSTEM_MODE = booleanPreferencesKey("followSystemMode")
        val REMINDER = booleanPreferencesKey("remind")
    }

    //==========================CATEGORY OPENED IN WORD COLLECTION============================================
    val categoryFlow = datastore.data
        .catch {exception ->
            if (exception is IOException) {
                Log.e(TAG_PETR, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val category = prefs[PrefKeys.CATEGORY] ?: ""
            category
        }

    suspend fun updateCategoryChosen(category: String) {
        datastore.edit { preferences ->
            preferences[PrefKeys.CATEGORY] = category
        }
    }

    //==========================TRANSLATION DIRECTION============================================
    val translationDirectionFlow = datastore.data
        .catch {exception ->
            if (exception is IOException) {
                Log.e(TAG_PETR, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val nativeToForeign = prefs[PrefKeys.TRANSLATION_DIRECTION] ?: false
            nativeToForeign
        }

    suspend fun updatetranslationDirection(nativeToForeign: Boolean) {
        datastore.edit { preferences ->
            preferences[PrefKeys.TRANSLATION_DIRECTION] = nativeToForeign
        }
    }

    //==========================MODE============================================
    val modeFlow = datastore.data
        .catch {exception ->
            if (exception is IOException) {
                Log.e(TAG_PETR, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val mode = prefs[PrefKeys.MODE] ?: 0
            mode
        }

    suspend fun updateMode(mode: Int) {
        datastore.edit { prefs ->
            prefs[PrefKeys.MODE] = mode
        }
    }

    //===============================IS FOLLOWING SYSTEM MODE=======================
    val followSystemModeFlow = datastore.data
        .catch {exception ->
            if (exception is IOException) {
                Log.e(TAG_PETR, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val follow = prefs[PrefKeys.FOLLOW_SYSTEM_MODE] ?: false
            follow
        }

    suspend fun updateFollowSystemMode(follow: Boolean) {
        datastore.edit { it[PrefKeys.FOLLOW_SYSTEM_MODE] = follow }
    }

    //===============================REMINDER=======================
    val remindFlow = datastore.data
        .catch {exception ->
            if (exception is IOException) {
                Log.e(TAG_PETR, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val remind = prefs[PrefKeys.REMINDER] ?: false
            remind
        }

    suspend fun updateRemind(remind: Boolean) {
        datastore.edit { it[PrefKeys.REMINDER] = remind }
    }
}




















package space.rodionov.porosenokpetr.feature_driller.data.storage

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.Constants.TAG_PETR
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.datastore by preferencesDataStore("datastore")

//@Singleton
class Datastore /*@Inject constructor*/(
   /* @ApplicationContext*/ app: Application
) {

    private val datastore = app.datastore

    private object PrefKeys {
        val CATEGORY = stringPreferencesKey("catName")
        val TRANSLATION_DIRECTION = booleanPreferencesKey(("transDir"))
    }

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
            Log.d(TAG_PETR, "updateCategoryChosen: $category")
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
            Log.d(TAG_PETR, "updateCategoryChosen: $nativeToForeign")
        }
    }
}
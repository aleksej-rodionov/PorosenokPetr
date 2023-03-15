package space.rodionov.porosenokpetr.core.data.preferences

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.core.domain.preferences.Preferences
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_EN
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_RU
import space.rodionov.porosenokpetr.core.util.Constants.LANGUAGE_SE
import space.rodionov.porosenokpetr.core.util.Constants.TAG_NATIVE_LANG
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import java.io.IOException

private val Context.datastore by preferencesDataStore("datastore")

//@Singleton
class PreferencesImpl /*@Inject constructor*/(
   /* @ApplicationContext*/ app: Application
): Preferences {

    private val datastore = app.datastore

    private object PrefKeys {
        val CATEGORY = stringPreferencesKey("catName")
        val TRANSLATION_DIRECTION = booleanPreferencesKey("transDir")
        val MODE = intPreferencesKey("mode")
        val FOLLOW_SYSTEM_MODE = booleanPreferencesKey("followSystemMode")
        val REMINDER = booleanPreferencesKey("remind")
        val MILLIS_FROM_DAY_BEGINNING = longPreferencesKey("millis")
        val NATIVE_LANGUAGE = intPreferencesKey("nativeLanguage")
        val LEARNED_LANGUAGE = intPreferencesKey("learnedLanguage")
//        val FOLLOW_SYSTEM_LOCALE = booleanPreferencesKey("followSystemLocale")
    }

    //==========================CATEGORY OPENED IN WORD COLLECTION============================================
    override fun categoryFlow() = datastore.data
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

    override suspend fun updateCategoryChosen(category: String) {
        datastore.edit { preferences ->
            preferences[PrefKeys.CATEGORY] = category
        }
    }

    //==========================TRANSLATION DIRECTION============================================
    override fun translationDirectionFlow() = datastore.data
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

   override suspend fun updatetranslationDirection(nativeToForeign: Boolean) {
        datastore.edit { preferences ->
            preferences[PrefKeys.TRANSLATION_DIRECTION] = nativeToForeign
        }
    }

    //==========================MODE============================================
    override fun modeFlow() = datastore.data
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

    override suspend fun updateMode(mode: Int) {
        datastore.edit { prefs ->
            prefs[PrefKeys.MODE] = mode
        }
    }

    //===============================IS FOLLOWING SYSTEM MODE=======================
    override fun followSystemModeFlow() = datastore.data
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

    override suspend fun updateFollowSystemMode(follow: Boolean) {
        datastore.edit { it[PrefKeys.FOLLOW_SYSTEM_MODE] = follow }
    }

    //==========================NATIVE LANGUAGE============================================
    override fun nativeLanguageFlow() = datastore.data
        .catch {exception ->
            if (exception is IOException) {
                Log.e(TAG_PETR, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val language = prefs[PrefKeys.NATIVE_LANGUAGE] ?: LANGUAGE_RU
            language
        }

    override suspend fun updateNativeLanguage(language: Int) {
        Log.d(TAG_NATIVE_LANG, "updateNativeLanguage: $language")
        datastore.edit { prefs ->
            prefs[PrefKeys.NATIVE_LANGUAGE] = language
        }
    }

    //==========================LEARNED LANGUAGE============================================
    override fun learnedLanguageFlow() = datastore.data
        .catch {exception ->
            if (exception is IOException) {
                Log.e(TAG_PETR, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val language = prefs[PrefKeys.LEARNED_LANGUAGE] ?: if (BuildConfig.FLAVOR == "swedishdriller") LANGUAGE_SE else LANGUAGE_EN
            language
        }

    override suspend fun updateLearnedLanguage(language: Int) {
        Log.d(TAG_NATIVE_LANG, "updateLearneLanguage: $language")
        datastore.edit { prefs ->
            prefs[PrefKeys.LEARNED_LANGUAGE] = language
        }
    }
}




















package space.rodionov.porosenokpetr.core.data.preferences

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.core.domain.preferences.KeyValueStorage
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import java.io.IOException

private val Context.datastore by preferencesDataStore("datastore")

class KeyValueStorageImpl(
    app: Application
) : KeyValueStorage {

    private val datastore = app.datastore
    private val sharedPrefs = app.applicationContext.getSharedPreferences("datastore", Context.MODE_PRIVATE)

    private val gson = GsonBuilder().create()
    private val stringListType = object : TypeToken<List<String?>?>() {}.type

    override fun has(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(key: String) {
        TODO("Not yet implemented")
    }

    override fun setValue(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    override suspend fun updateValue(key: String, value: String) {
        datastore.edit { it[stringPreferencesKey(key)] = value }
    }

    override fun getValue(key: String, defaultValue: String): String {
        return sharedPrefs.getString(key, defaultValue) ?: defaultValue
    }

    override fun collectValue(key: String, defaultValue: String): Flow<String> {
        return datastore.data
            .catch { handleReadingPreferencesException(it) }
            .map { it[stringPreferencesKey(key)] ?: defaultValue }
    }


    override fun setValue(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }

    override suspend fun updateValue(key: String, value: Int) {
        datastore.edit { it[intPreferencesKey(key)] = value }
    }

    override fun getValue(key: String, defaultValue: Int): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    override fun collectValue(key: String, defaultValue: Int): Flow<Int> {
        return datastore.data
            .catch { handleReadingPreferencesException(it) }
            .map { it[intPreferencesKey(key)] ?: defaultValue }
    }


    override fun setValue(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }

    override suspend fun updateValue(key: String, value: Boolean) {
        datastore.edit { it[booleanPreferencesKey(key)] = value }
    }

    override fun getValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue)
    }

    override fun collectValue(key: String, defaultValue: Boolean): Flow<Boolean> {
        return datastore.data
            .catch { handleReadingPreferencesException(it) }
            .map { it[booleanPreferencesKey(key)] ?: defaultValue }
    }



    override fun collectListValue(key: String, defaultValue: List<String>): Flow<List<String>> {
        return datastore.data
            .catch { handleReadingPreferencesException(it) }
            .map {
                val rawJson = it[stringPreferencesKey(key)]
                rawJson?.let {
                    gson.fromJson(rawJson, stringListType)
                } ?: defaultValue
            }
    }

    override suspend fun updateListValue(key: String, value: List<String>) {
        val rawJson = gson.toJson(value, stringListType)
        updateValue(key, rawJson)
    }

    private suspend fun FlowCollector<Preferences>.handleReadingPreferencesException(
        exception: Throwable
    ) {
        if (exception is IOException) {
            Log.e(TAG_PETR, "Error reading preferences", exception)
            this.emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}




















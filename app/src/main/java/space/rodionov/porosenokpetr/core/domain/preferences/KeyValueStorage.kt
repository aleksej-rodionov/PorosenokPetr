package space.rodionov.porosenokpetr.core.domain.preferences

import kotlinx.coroutines.flow.Flow

interface KeyValueStorage {

    fun has(key: String): Boolean

    fun remove(key: String)

    fun setValue(key: String, value: String)
    suspend fun updateValue(key: String, value: String)
    fun getValue(key: String, defaultValue: String): String
    fun collectValue(key: String, defaultValue: String): Flow<String>


    fun setValue(key: String, value: Int)
    suspend fun updateValue(key: String, value: Int)
    fun getValue(key: String, defaultValue: Int): Int
    fun collectValue(key: String, defaultValue: Int): Flow<Int>


    fun setValue(key: String, value: Boolean)
    suspend fun updateValue(key: String, value: Boolean)
    fun getValue(key: String, defaultValue: Boolean): Boolean
    fun collectValue(key: String, defaultValue: Boolean): Flow<Boolean>


    fun collectListValue(key: String, defaultValue: List<String>): Flow<List<String>>

    suspend fun updateListValue(key: String, value: List<String>)
}
package space.rodionov.porosenokpetr.core.domain.preferences

import kotlinx.coroutines.flow.Flow

interface KeyValueStorage {

    fun has(key: String): Boolean

    fun remove(key: String)

    fun collectValue(key: String, defaultValue: String): Flow<String>

    suspend fun updateValue(key: String, value: String)

    fun collectValue(key: String, defaultValue: Int): Flow<Int>

    suspend fun updateValue(key: String, value: Int)

    fun collectValue(key: String, defaultValue: Boolean): Flow<Boolean>

    suspend fun updateValue(key: String, value: Boolean)
}
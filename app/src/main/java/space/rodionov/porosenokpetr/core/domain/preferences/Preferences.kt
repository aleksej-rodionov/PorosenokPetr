package space.rodionov.porosenokpetr.core.domain.preferences

import kotlinx.coroutines.flow.Flow

interface Preferences {

    fun categoryFlow(): Flow<String>
    suspend fun updateCategoryChosen(category: String)

    fun modeFlow(): Flow<Int>
    suspend fun updateMode(mode: Int)

    fun followSystemModeFlow(): Flow<Boolean>
    suspend fun updateFollowSystemMode(follow: Boolean)

    fun translationDirectionFlow(): Flow<Boolean>
    suspend fun updatetranslationDirection(nativeToForeign: Boolean)

    fun nativeLanguageFlow(): Flow<Int>
    suspend fun updateNativeLanguage(newLanguage: Int)

    fun learnedLanguageFlow(): Flow<Int>
    suspend fun updateLearnedLanguage(newLanguage: Int)

}
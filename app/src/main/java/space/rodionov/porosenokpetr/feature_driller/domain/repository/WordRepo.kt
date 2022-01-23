package space.rodionov.porosenokpetr.feature_driller.domain.repository

import space.rodionov.porosenokpetr.feature_driller.domain.models.Word

interface WordRepo {

    suspend fun getTenWords() : List<Word>

    fun getMode() : Boolean
    fun setMode(isNight: Boolean)
}
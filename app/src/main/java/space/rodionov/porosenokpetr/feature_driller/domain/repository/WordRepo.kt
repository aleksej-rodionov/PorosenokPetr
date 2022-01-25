package space.rodionov.porosenokpetr.feature_driller.domain.repository

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word

interface WordRepo {

    fun getTenWords(): Flow<Resource<List<Word>>>

    fun getMode(): Boolean
    fun setMode(isNight: Boolean)
}
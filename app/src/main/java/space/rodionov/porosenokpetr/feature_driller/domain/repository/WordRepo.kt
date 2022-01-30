package space.rodionov.porosenokpetr.feature_driller.domain.repository

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.CategoryWithWords
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word

interface WordRepo {

    fun getTenWords(): Flow<Resource<List<Word>>>

    suspend fun updateWordIsActive(word: Word, isActive: Boolean)

    suspend fun getRandomWord() : Word

    fun observeAllCategories() : Flow<List<Category>>

    fun observeAllCategoriesWithWords() : Flow<List<CategoryWithWords>>

    suspend fun makeCategoryActive(catName: String, makeActive: Boolean)

    suspend fun getAllActiveCatsNames(): List<String>

    suspend fun getAllCatsNames(): List<String>

    suspend fun isCatActive(name: String): Boolean

    fun getMode(): Boolean

    fun setMode(isNight: Boolean)
}
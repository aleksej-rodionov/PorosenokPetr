package space.rodionov.porosenokpetr.core.domain.repository

import kotlinx.coroutines.flow.Flow
import space.rodionov.porosenokpetr.core.util.Resource
import space.rodionov.porosenokpetr.core.domain.model.CatWithWords
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word

interface WordRepo {

    suspend fun insertCategory(category: Category)
    suspend fun insertWord(word: Word)

    suspend fun getTenWords(): List<Word>

    suspend fun getAllWords(): List<Word>
    suspend fun getWordsQuantity(): Int

    fun observeAllCategories() : Flow<List<Category>>

    suspend fun getAllCategories() : List<Category>

    fun observeWordsBySearchQueryInCategories(
        searchQuery: String,
        categories: List<String>
    ): Flow<List<Word>>

    suspend fun getWordsBySearchQueryInCategories(
        searchQuery: String,
        categories: List<Category>
    ): List<Word>

    suspend fun getWordsByCat(catName: String): List<Word>

    suspend fun updateLearnedPercentInCategory(catName: String, learnedPercent: Int)

    fun wordsBySearchQuery(catName: String, searchQuery: String) : Flow<List<Word>>

    suspend fun updateWordStatus(word: Word, status: Int)

    suspend fun updateWord(word: Word)

    suspend fun getWordById(id: Int): Word?

    suspend fun getRandomWordFromActiveCats(activeCatsNames: List<String>) : Word

    fun observeWord(nativ: String, foreign: String, categoryName: String) : Flow<Word>

    fun observeAllCategoriesWithWords() : Flow<List<CatWithWords>>

    suspend fun makeCategoryActive(catName: String, makeActive: Boolean)

    suspend fun getAllActiveCatsNames(): List<String>

    suspend fun getAllCatsNames(): List<String>

    suspend fun isCatActive(name: String): Boolean

    fun observeAllActiveCatsNames(): Flow<List<String>>
}










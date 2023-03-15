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

//    fun observeWords(query: String): Flow<Resource<List<Word>>>

    suspend fun updateWord(word: Word, newWord: Word)

    suspend fun updateWordIsActive(word: Word, isActive: Boolean)
    suspend fun updateIsWordActive(nativ: String, foreign: String, catName: String, isActive: Boolean)

    suspend fun getRandomWordFromActiveCats(activeCatsNames: List<String>) : Word

    fun observeWord(nativ: String, foreign: String, categoryName: String) : Flow<Word>

    fun wordsBySearchQuery(catName: String, searchQuery: String) : Flow<List<Word>>

    fun observeAllCategories() : Flow<List<Category>>

    fun observeAllCategoriesWithWords() : Flow<List<CatWithWords>>

    suspend fun makeCategoryActive(catName: String, makeActive: Boolean)

    suspend fun getAllActiveCatsNames(): List<String>

    suspend fun getAllCatsNames(): List<String>

    suspend fun isCatActive(name: String): Boolean

    fun observeAllActiveCatsNames(): Flow<List<String>>
}










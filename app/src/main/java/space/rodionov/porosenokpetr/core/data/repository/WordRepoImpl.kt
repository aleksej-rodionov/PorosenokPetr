package space.rodionov.porosenokpetr.core.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.core.data.local.WordDao
import space.rodionov.porosenokpetr.core.data.local.mapper.toCategory
import space.rodionov.porosenokpetr.core.data.local.mapper.toCategoryEntity
import space.rodionov.porosenokpetr.core.data.local.mapper.toWord
import space.rodionov.porosenokpetr.core.data.local.mapper.toWordEntity
import space.rodionov.porosenokpetr.core.domain.model.CatWithWords
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class WordRepoImpl(
    private val dao: WordDao,
) : WordRepo {

    override suspend fun insertCategory(category: Category) {
        dao.insertCategory(category.toCategoryEntity())
    }

    override suspend fun insertWord(word: Word) {
        dao.insertWord(word.toWordEntity())
    }

    override suspend fun getTenWords(): List<Word> {
        return dao.getTenWords().map {
            Log.d("TAG_DB", "getTenWords: ${it.swe}")
            it.toWord()
        }
    }

    override suspend fun getAllWords() = dao.getAllWords().map { it.toWord() }
    override suspend fun getWordsQuantity(): Int = dao.getAllWords().size

    override fun observeAllCategories(): Flow<List<Category>> =
        dao.observeAllCategories().map { cats ->
            cats.map { it.toCategory() }
        }

    override suspend fun getAllCategories(): List<Category> {
        return dao.getAllCategories().map { it.toCategory() }
    }

    override fun observeWordsBySearchQueryInCategories(
        searchQuery: String,
        categories: List<String>
    ): Flow<List<Word>> {
        return dao.observeWordsBySearchQueryInCategories(
            searchQuery,
            categories
        ).map { entityList ->
            entityList.map {
                it.toWord()
            }
        }
    }

    override suspend fun getWordsBySearchQueryInCategories(
        searchQuery: String,
        categories: List<Category>
    ): List<Word> {
        return dao.getWordsBySearchQueryInCategories(
            searchQuery,
            categories.map { it.name }
        ).map {
            it.toWord()
        }
    }

    override suspend fun getWordsByCat(catName: String): List<Word> {
        return dao.getWordsByCat(catName).map {
            it.toWord()
        }
    }

    override suspend fun updateLearnedPercentInCategory(catName: String, learnedPercent: Int) {
        val categoryEntity = dao.getCategoryByName(catName)
        dao.updateCategory(categoryEntity.copy(learnedFromActivePercentage = learnedPercent))
    }

    override fun wordsBySearchQuery(catName: String, searchQuery: String) =
        dao.observeWords(catName, searchQuery).map { words ->
            words.map {
                it.toWord()
            }
        }

    override suspend fun getWordById(id: Int): Word? {
        return dao.getWordById(id)?.toWord()
    }

    override suspend fun updateWord(word: Word) {
        dao.updateWord(word.toWordEntity())
    }

    override fun observeWord(
        nativ: String,
        foreign: String,
        categoryName: String
    ): Flow<Word> {
        return dao.observeWord(nativ, foreign, categoryName).map { it.toWord() }
    }

    override suspend fun getRandomWordFromActiveCats(activeCatsNames: List<String>): Word {
        return dao.getRandomWordFromActiveCats(activeCatsNames).toWord()
    }

    override fun observeAllCategoriesWithWords(): Flow<List<CatWithWords>> {
        return dao.observeAllCategoriesWithWords().map { cwws ->
            cwws.map {
                val category = it.categoryEntity.toCategory()
                val words = it.words.map { we -> we.toWord() }
                CatWithWords(category, words)
            }
        }
    }

    override suspend fun makeCategoryActive(catName: String, makeActive: Boolean) {
        val categoryEntity = dao.getCategoryByName(catName)
        dao.updateCategory(categoryEntity.copy(isCategoryActive = makeActive))
    }

    override suspend fun getAllActiveCatsNames(): List<String> {
        return dao.getALlActiveCatsNames()
    }

    override suspend fun getAllCatsNames(): List<String> {
        return dao.getAllCatNames()
    }

    override suspend fun isCatActive(name: String): Boolean = dao.isCategoryActive(name)

    override fun observeAllActiveCatsNames(): Flow<List<String>> = dao.observeAllActiveCatsNames()
}
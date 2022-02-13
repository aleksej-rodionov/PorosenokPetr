package space.rodionov.porosenokpetr.feature_driller.data.repository

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.Constants.TAG_PETR
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.data.local.WordDao
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.WordEntity
import space.rodionov.porosenokpetr.feature_driller.data.storage.Datastore
import space.rodionov.porosenokpetr.feature_driller.data.storage.Storage
import space.rodionov.porosenokpetr.feature_driller.domain.models.CatWithWords
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class WordRepoImpl(
    private val dao: WordDao,
    private val sharedPref: Storage,
    private val datastore: Datastore
) : WordRepo {

    override fun getTenWords(): Flow<Resource<List<Word>>> = flow {
        emit(Resource.Loading())
        delay(500L) // для пробы
        val words = dao.getTenWords().map { it.toWord() }
        emit(Resource.Success(words))
        // todo обработать Resource.Error ??
    }

    override suspend fun updateWordIsActive(word: Word, isActive: Boolean) {
        val wordEntity = dao.getWord(word.nativ, word.foreign, word.categoryName)
        wordEntity.let {
            Log.d(TAG_PETR, "updateWordIsActive: word found and changed")
            dao.updateWord(it.copy(isWordActive = isActive))
        }
    }

    override suspend fun updateIsWordActive(nativ: String, foreign: String, catName: String, isActive: Boolean) {
        val wordEntity = dao.getWord(nativ, foreign, catName)
        wordEntity.let {
            Log.d(TAG_PETR, "updateWordIsActive: word found and changed")
            dao.updateWord(it.copy(isWordActive = isActive))
        }
    }

    override fun observeWord(
        nativ: String,
        foreign: String,
        categoryName: String
    ): Flow<Word> {
        return dao.observeWord(nativ, foreign, categoryName).map {
            it.let {we->
                we.toWord()
            }
        }
    }

    override suspend fun getRandomWordFromActiveCats(activeCatsNames: List<String>): Word {
        return dao.getRandomWordFromActiveCats(activeCatsNames).toWord()
    }

    override fun wordsBySearchQuery(catName: String, searchQuery: String) =
        dao.observeWords(catName, searchQuery).map { words ->
            words.map {
                it.toWord()
            }
        }

    override fun observeAllCategories(): Flow<List<Category>> =
        dao.observeAllCategories().map { cats ->
            cats.map { it.toCategory() }
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

    override fun getMode(): Boolean = sharedPref.getMode()
    override fun setMode(isNight: Boolean) = sharedPref.setMode(isNight)

    override fun storageCatName(): Flow<String> = datastore.categoryFlow
    override suspend fun updateStorageCat(catName: String) = datastore.updateCategoryChosen(catName)
}
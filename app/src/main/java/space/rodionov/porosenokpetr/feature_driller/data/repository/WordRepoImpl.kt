package space.rodionov.porosenokpetr.feature_driller.data.repository

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.Constants
import space.rodionov.porosenokpetr.feature_driller.data.local.WordDao
import space.rodionov.porosenokpetr.feature_driller.data.local.entity.CategoryWithWords
import space.rodionov.porosenokpetr.feature_driller.data.storage.Storage
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class WordRepoImpl(
    private val dao: WordDao,
    private val sharedPref: Storage
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
        Log.d(Constants.TAG_PETR, "updateWordActivity: wordEntity.foreign = ${wordEntity.foreign}, newActiveValue = $isActive")
        dao.updateWord(wordEntity.copy(isWordActive = isActive))
    }

    override suspend fun getRandomWordFromActiveCats(activeCatsNames: List<String>): Word {
        return dao.getRandomWordFromActiveCats(activeCatsNames).toWord()
    }

    override fun observeAllCategories(): Flow<List<Category>> =
        dao.observeAllCategories().map { cats ->
            cats.map { it.toCategory() }
        }

    override fun observeAllCategoriesWithWords(): Flow<List<CategoryWithWords>> =
        dao.observeAllCategoriesWithWords() // todo и как это имплементировать между слоями? Надо ли CatWithWordsEntity делать?

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

    override suspend fun isCatActive(name: String): Boolean {
        val isActive = dao.isCategoryActive(name)
//        Log.d(TAG_PETR, "REPOSITORY isCatActive(): cat = $name, isActive = $isActive")
        return isActive
    }

    override fun observeAllActiveCatsNames(): Flow<List<String>> = dao.observeAllActiveCatsNames()

    override fun getMode(): Boolean {
        return sharedPref.getMode()
    }

    override fun setMode(isNight: Boolean) {
        sharedPref.setMode(isNight)
    }
}
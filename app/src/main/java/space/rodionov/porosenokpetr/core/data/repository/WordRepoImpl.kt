package space.rodionov.porosenokpetr.core.data.repository

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import space.rodionov.porosenokpetr.BuildConfig
import space.rodionov.porosenokpetr.core.util.Constants.TAG_PETR
import space.rodionov.porosenokpetr.core.util.Resource
import space.rodionov.porosenokpetr.core.data.local.WordDao
import space.rodionov.porosenokpetr.core.domain.model.CatWithWords
import space.rodionov.porosenokpetr.core.domain.model.Category
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.WordRepo

class WordRepoImpl(
    private val dao: WordDao,
//    private val preferences: PreferencesImpl
) : WordRepo {

    override fun getTenWords(): Flow<Resource<List<Word>>> = flow {
        emit(Resource.Loading())
        delay(500L) // для пробы
        val words = dao.getTenWords().map { it.toWord() }
        emit(Resource.Success(words))
        // todo обработать Resource.Error ?? (когда будет API)
    }

    override suspend fun updateWordIsActive(word: Word, isActive: Boolean) {
        val wordEntity = dao.getWord(word.rus, word.eng, word.categoryName)
        wordEntity.let {
            Log.d(TAG_PETR, "updateWordIsActive: word found and changed")
            dao.updateWord(it.copy(isWordActive = isActive))
        }
    }

    override suspend fun updateWord(word: Word, newWord: Word) {
        val wordEntity = dao.getWord(word.rus, word.eng, word.categoryName)
        if (BuildConfig.FLAVOR == "englishdriller") {
            wordEntity.let {
                dao.updateWord(
                    it.copy(
                        rus = newWord.rus,
                        eng = newWord.eng
                    )
                )
            }
        }
            if (BuildConfig.FLAVOR == "swedishdriller") {
            wordEntity.let {
                dao.updateWord(
                    it.copy(
                        newWord.rus,
                        newWord.ukr,
                        newWord.eng,
                        newWord.swe
                    )
                )
            }
        }
    }

    override suspend fun updateIsWordActive(
        nativ: String,
        foreign: String,
        catName: String,
        isActive: Boolean
    ) {
        val wordEntity = dao.getWord(nativ, foreign, catName)
        wordEntity.let {
            Log.d(TAG_PETR, "updateWordIsActive: word found and changed")
            dao.updateWord(it.copy(isWordActive = isActive))
        }
    }

    override fun observeWord(
        rus: String,
        eng: String,
        categoryName: String
    ): Flow<Word> {
        return dao.observeWord(rus, eng, categoryName).map {
            it.let { we ->
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

//    override fun getMode(): Flow<Int> = preferences.modeFlow
//    override suspend fun setMode(mode: Int) = preferences.updateMode(mode)
//
//    override fun getFollowSystemMode(): Flow<Boolean> = preferences.followSystemModeFlow
//    override suspend fun setFollowSystemMode(follow: Boolean) = preferences.updateFollowSystemMode(follow)
//
//    override fun getRemind(): Flow<Boolean> = preferences.remindFlow
//    override suspend fun setRemind(remind: Boolean)  = preferences.updateRemind(remind)
//
//    override fun getNotifyMillis(): Flow<Long> = preferences.millisFlow
//    override suspend fun setNotifyMillis(millis: Long) = preferences.updateMillis(millis)
//
//    override fun getTransDir(): Flow<Boolean> = preferences.translationDirectionFlow
//    override suspend fun setTransDir(nativeToForeign: Boolean) {
//        preferences.updatetranslationDirection(nativeToForeign)
//    }
//
//    override fun observeNativeLanguage(): Flow<Int> = preferences.nativeLanguageFlow
//    override suspend fun updateNativeLanguage(newLanguage: Int) = preferences.updateNativeLanguage(newLanguage)
//
//    override fun observeLearnedLanguage(): Flow<Int> = preferences.learnedLanguageFlow
//    override suspend fun updateLearnedLanguage(newLanguage: Int) = preferences.updateLearnedLanguage(newLanguage)
//
//    override fun storageCatName(): Flow<String> = preferences.categoryFlow
//    override suspend fun updateStorageCat(catName: String) = preferences.updateCategoryChosen(catName)
}
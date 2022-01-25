package space.rodionov.porosenokpetr.feature_driller.data.repository

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import space.rodionov.porosenokpetr.core.Resource
import space.rodionov.porosenokpetr.feature_driller.Constants
import space.rodionov.porosenokpetr.feature_driller.data.local.WordDao
import space.rodionov.porosenokpetr.feature_driller.data.storage.Storage
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class WordRepoImpl(
    private val dao: WordDao,
    private val sharedPref: Storage
):WordRepo {

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

    override fun getMode(): Boolean {
       return sharedPref.getMode()
    }

    override fun setMode(isNight: Boolean) {
        sharedPref.setMode(isNight)
    }
}
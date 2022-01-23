package space.rodionov.porosenokpetr.feature_driller.data.local.repository

import space.rodionov.porosenokpetr.feature_driller.data.local.WordDao
import space.rodionov.porosenokpetr.feature_driller.data.storage.Storage
import space.rodionov.porosenokpetr.feature_driller.domain.models.Word
import space.rodionov.porosenokpetr.feature_driller.domain.repository.WordRepo

class WordRepoImpl(
    private val dao: WordDao,
    private val sharedPref: Storage
):WordRepo {

    override suspend fun getTenWords(): List<Word> {
        return dao.getTenWords().map { it.toWord() }
    }

    override fun getMode(): Boolean {
       return sharedPref.getMode()
    }

    override fun setMode(isNight: Boolean) {
        sharedPref.setMode(isNight)
    }
}
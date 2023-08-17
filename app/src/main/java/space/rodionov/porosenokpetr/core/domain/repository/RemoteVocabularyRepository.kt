package space.rodionov.porosenokpetr.core.domain.repository

import space.rodionov.porosenokpetr.core.domain.model.Word

interface RemoteVocabularyRepository {

    suspend fun fetchAllWords(): List<Word>
}
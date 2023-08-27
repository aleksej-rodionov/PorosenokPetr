package space.rodionov.porosenokpetr.core.domain.repository

import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw

interface RemoteVocabularyRepository {

    suspend fun fetchAllWords(): List<WordRaw>
}
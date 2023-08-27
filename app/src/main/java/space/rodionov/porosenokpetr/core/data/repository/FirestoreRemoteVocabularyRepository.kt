package space.rodionov.porosenokpetr.core.data.repository

import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.data.remote.FirestoreRemoteVocabulary
import space.rodionov.porosenokpetr.core.data.remote.mapper.toWordRaw
import space.rodionov.porosenokpetr.core.domain.repository.RemoteVocabularyRepository

class FirestoreRemoteVocabularyRepository(
    private val firestoreRemoteVocabulary: FirestoreRemoteVocabulary
): RemoteVocabularyRepository {

    override suspend fun fetchAllWords(): List<WordRaw> {
        return firestoreRemoteVocabulary.fetchAllWords().map {
            it.toWordRaw()
        }
    }
}
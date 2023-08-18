package space.rodionov.porosenokpetr.core.data.repository

import space.rodionov.porosenokpetr.core.data.remote.FirestoreRemoteVocabulary
import space.rodionov.porosenokpetr.core.domain.model.Word
import space.rodionov.porosenokpetr.core.domain.repository.RemoteVocabularyRepository

class FirestoreRemoteVocabularyRepository(
    private val firestoreRemoteVocabulary: FirestoreRemoteVocabulary
): RemoteVocabularyRepository {

//    override suspend fun fetchAllWords(): List<Word> {
//        return firestoreRemoteVocabulary.fetchAllWords().map {
//            it.toWord()
//        }
//    }
}
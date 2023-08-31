package space.rodionov.porosenokpetr.core.data.repository

import android.util.Log
import space.rodionov.porosenokpetr.core.data.local.entity.WordRaw
import space.rodionov.porosenokpetr.core.data.remote.FirestoreRemoteVocabulary
import space.rodionov.porosenokpetr.core.data.remote.mapper.toWordRaw
import space.rodionov.porosenokpetr.core.domain.repository.RemoteVocabularyRepository
import java.lang.Exception

private const val TAG = "WordMappingTAG (repo)"

class FirestoreRemoteVocabularyRepository(
    private val firestoreRemoteVocabulary: FirestoreRemoteVocabulary
) : RemoteVocabularyRepository {

    override suspend fun fetchAllWords(): List<WordRaw> {

//        return firestoreRemoteVocabulary.fetchAllWordsTest().map { //todo use it for testing
        return firestoreRemoteVocabulary.fetchAllWords().map {
            try {
                it.toWordRaw()
            } catch (e: Exception) {
                Log.d(TAG, "fetchAllWords: error happened woth word $it")
                Log.d(TAG, "fetchAllWords: error message: ${e.message}")
                throw e
            }
        }
    }
}
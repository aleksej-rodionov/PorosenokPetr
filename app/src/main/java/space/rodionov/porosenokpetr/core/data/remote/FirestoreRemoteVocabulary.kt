package space.rodionov.porosenokpetr.core.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import space.rodionov.porosenokpetr.core.data.remote.model.WordDto

class FirestoreRemoteVocabulary {

    private val vocabularyCollectionRef =
        Firebase.firestore.collection(Constants.VOCABULARY_COLLECTION_PATH)

    suspend fun fetchAllWords(): List<WordDto> {

        //todo maybe I need batch here
        return try {
            val words = mutableListOf<WordDto>()
            val querySnapshot = vocabularyCollectionRef
                .orderBy("eng")
                .startAt("to have a")
                .endAt("to have a" + '\uf8ff')
                .get()
                .await()
            for (document in querySnapshot.documents) {
                val wordDto = document.toObject<WordDto>()
                wordDto?.let {
                    words.add(it)
                }
            }
            words
        } catch (e: Exception) {
            throw e
        }
    }
}
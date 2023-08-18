package space.rodionov.porosenokpetr.core.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import space.rodionov.porosenokpetr.core.data.remote.model.WordDto

class FirestoreRemoteVocabulary {

    private val vocabularyCollectionRef =
        Firebase.firestore.collection(Constants.VOCABULARY_COLLECTION_PATH)

//    suspend fun fetchAllWords(): List<WordDto> {
//        //todo
//    }
}
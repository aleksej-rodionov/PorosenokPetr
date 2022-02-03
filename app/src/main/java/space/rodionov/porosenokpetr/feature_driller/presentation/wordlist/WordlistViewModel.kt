package space.rodionov.porosenokpetr.feature_driller.presentation.wordlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import space.rodionov.porosenokpetr.Constants.TAG_PETR
import space.rodionov.porosenokpetr.feature_driller.domain.models.Category
import javax.inject.Inject

@HiltViewModel
class WordlistViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {
    var catToSearchIn = state.getLiveData<Category>("category")
//        set(value) {
//            field = value
//            Log.d(TAG_PETR, "wordsViewModel: category updated = $value")
//            state.set("category", value)
//        }


}
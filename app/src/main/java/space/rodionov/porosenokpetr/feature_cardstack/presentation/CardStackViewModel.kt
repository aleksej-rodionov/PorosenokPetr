package space.rodionov.porosenokpetr.feature_cardstack.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem
import javax.inject.Inject

class CardStackViewModel @Inject constructor(

): ViewModel() {

    var state by mutableStateOf(CardstackState())
        private set

    fun onEvent(event: CardstackEvent) {
        when (event) {
            //todo
        }
    }
}

data class CardstackState(
    val words: List<CardStackItem.WordUi> = emptyList()
)

sealed class CardstackEvent {

}
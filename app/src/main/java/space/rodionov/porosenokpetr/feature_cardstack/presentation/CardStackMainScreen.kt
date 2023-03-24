package space.rodionov.porosenokpetr.feature_cardstack.presentation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts.CardStackView

const val TAG_CARDSTACK = "TAG_CARDSTACK"

@Composable
fun CardStackMainScreen(
    scaffoldState: ScaffoldState,
    owner: ComponentActivity,
    factory: ViewModelFactory
) {

    val viewModel by owner.viewModels<CardStackViewModel> { factory }
    val state = viewModel.state

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CardStack(
            state = state,
            updateCurrentPosition = {
                viewModel.onEvent(CardstackEvent.UpdateCurrentPosition(it))
            },
            updateWordStatus = {
                viewModel.onEvent(CardstackEvent.UpdateWordStatus(it))
            },
            speakWord = {
                viewModel.onEvent(CardstackEvent.SpeakWord(it))
            }
        )
    }
}



@Composable
fun CardStack(
    state: CardstackState,
    updateCurrentPosition: (Int) -> Unit,
    updateWordStatus: (Int) -> Unit,
    speakWord: (String) -> Unit
) {

    val context = LocalContext.current

    val view = CardStackView(context)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            view.apply {
                setOnWordAppearedListener {
                    updateCurrentPosition(it)
                }
                setOnWordSwipedListener {
                    updateWordStatus(it)
                }
                setOnSpeakWordListener {
                    speakWord(it)
                }
            }
        },
        update = {
            it.initView(state)
        }
    )
}
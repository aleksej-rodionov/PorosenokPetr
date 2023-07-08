package space.rodionov.porosenokpetr.feature_cardstack.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts.CardStackView

const val TAG_CARDSTACK = "TAG_CARDSTACK"

@Composable
fun CardStackMainScreen(
    scaffoldState: ScaffoldState,
    state: CardstackState,
    onEvent: (CardstackEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background), //todo Gray300 was
        contentAlignment = Alignment.Center
    ) {

        val spacing = LocalSpacing.current

        CardStack(
            state = state,
            updateCurrentPosition = {
                onEvent(CardstackEvent.UpdateCurrentPosition(it))
            },
            updateWordStatus = {
                onEvent(CardstackEvent.UpdateWordStatus(it))
            },
            speakWord = {
                onEvent(CardstackEvent.SpeakWord(it))
            }
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    end = spacing.spaceSmall,
                    top = 5.dp
                ),
            onClick = {
                //todo
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                contentDescription = "More",
                tint = MaterialTheme.colors.onBackground
            )
        }
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
        modifier = Modifier
//            .background(color = MaterialTheme.colors.background)
            .fillMaxSize(),
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
                initView(state)
            }
        },
        update = {
            it.submitList(state.words)
        }
    )
}

@Preview
@Composable
fun CardstackScreenPreview(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    CardStackMainScreen(
        scaffoldState = scaffoldState,
        state = CardstackState(),
        onEvent = {}
    )
}
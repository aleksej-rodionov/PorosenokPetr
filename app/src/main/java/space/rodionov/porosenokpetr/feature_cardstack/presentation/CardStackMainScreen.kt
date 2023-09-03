package space.rodionov.porosenokpetr.feature_cardstack.presentation

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_cardstack.presentation.components.CardStackView
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

const val TAG_CARDSTACK = "TAG_CARDSTACK"

@Composable
fun CardStackMainScreen(
    navigateTo: (String) -> Unit,
    state: CardstackState,
    uiEffect: Flow<UiEffect>,
    onEvent: (CardstackEvent) -> Unit
) {

    Log.d(TAG_CARDSTACK, "CardStackMainScreen: currentPosition = ${state.currentPosition}")

    LaunchedEffect(key1 = true) {
        uiEffect.collectLatest { event ->
            when (event) {
                is UiEffect.NavigateTo -> {
                    navigateTo(event.route)
                }

                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {

        val spacing = LocalSpacing.current

        CardStack(
            state = state,
            updateWordAppearedPosition = {
                onEvent(CardstackEvent.WordAppearedPosition(it))
            },
            updateWordDisappearedPosition = {
                onEvent(CardstackEvent.WordDisappearedPosition(it))
            },
            updateWordStatus = {
                onEvent(CardstackEvent.UpdateWordStatus(it))
            },
            speakWord = {
                onEvent(CardstackEvent.OnSpeakWordClick(it))
            },
            editWord = {
                onEvent(CardstackEvent.OnEditWordClick(it))
            },
            refill = {
                onEvent(CardstackEvent.OnRefillClick)
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
    updateWordAppearedPosition: (Int) -> Unit,
    updateWordDisappearedPosition: (Int) -> Unit,
    updateWordStatus: (Int) -> Unit,
    speakWord: (String) -> Unit,
    editWord: (CardStackItem.WordUi) -> Unit,
    refill: () -> Unit
) {

    val context = LocalContext.current

    val view = CardStackView(context)

    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = {
            view.apply {
                setOnWordAppearedListener {
                    updateWordAppearedPosition(it)
                }
                setOnWordDisappearedListener {
                    updateWordDisappearedPosition(it)
                }
                setOnWordSwipedListener {
                    updateWordStatus(it)
                }
                setOnSpeakWordListener {
                    speakWord(it)
                }
                setOnEditWordListener {
                    editWord(it)
                }
                setOnRefillBtnClick {
                    refill()
                }
                initView(state)
            }
        },
        update = {
            it.updateView(state)
        }
    )
}

@Preview
@Composable
fun CardstackScreenPreview(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    CardStackMainScreen(
        navigateTo = {},
        state = CardstackState(),
        uiEffect = emptyFlow()
    ) {}
}
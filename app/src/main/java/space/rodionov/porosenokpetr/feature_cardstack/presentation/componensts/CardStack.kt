package space.rodionov.porosenokpetr.feature_cardstack.presentation.componensts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardstackState
import space.rodionov.porosenokpetr.feature_cardstack.presentation.model.CardStackItem

@Composable
fun CardStack(
    state: CardstackState,
    updateWordStatus: (CardStackItem.WordUi, Int) -> Unit
) {

    val context = LocalContext.current

    val view = CardStackView(context)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            view.apply {

            }
        },
        update = {
            it.initView(state)
        }
    )
}
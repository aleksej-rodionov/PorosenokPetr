package space.rodionov.porosenokpetr.feature_settings.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_settings.presentation.components.SettingsBottomDrawer
import space.rodionov.porosenokpetr.ui.theme.Gray900

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsMainScreen(
    onNavigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    owner: ComponentActivity,
    factory: ViewModelFactory
) {

    val spacing = LocalSpacing.current
    val viewModel by owner.viewModels<SettingsViewModel> { factory }
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val scope = rememberCoroutineScope()
    BackHandler(sheetState.isVisible) {
        scope.launch { sheetState.hide() }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is UiEffect.NavigateUp -> {
                    onNavigateUp()
                }
                is UiEffect.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        effect.msg.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    Divider()

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        sheetContent = {

            SettingsBottomDrawer(

            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = spacing.spaceSmall,
                    end = spacing.spaceSmall,
                    top = spacing.spaceExtraSmall
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        //todo viewModel.onEvent(VocabularyEvent.OnBackClick)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Gray900
                    )
                }

                Text(
                    text = stringResource(id = R.string.settings), //todo localize
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    color = Gray900
                )

                IconButton(
                    onClick = {
                        //todo
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                        contentDescription = "More",
                        tint = Gray900
                    )
                }
            }
        }
    }
}
package space.rodionov.porosenokpetr.feature_settings.presentation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.presentation.components.TopBar
import space.rodionov.porosenokpetr.core.util.Constants.MODE_DARK
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_settings.presentation.components.HeaderItem
import space.rodionov.porosenokpetr.feature_settings.presentation.components.SettingsBottomDrawer
import space.rodionov.porosenokpetr.feature_settings.presentation.components.SwitcherItem
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.components.PlainItem

private const val TAG_SWITCHER = "TAG_SWITCHER"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsMainScreen(
    onNavigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: SettingsViewModel,
//    owner: ComponentActivity,
//    factory: ViewModelFactory
) {

    val spacing = LocalSpacing.current
//    val viewModel by owner.viewModels<SettingsViewModel> { factory }
    val context = LocalContext.current
    val state = viewModel.state

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
        ) {

            Divider()

            TopBar(
                text = R.string.settings,
                onBackClick = {
                    viewModel.onEvent(SettingsEvent.OnBackClick)
                },
                onMenuClick = {
                    //todo
                }
            )

            Divider(Modifier.padding(bottom = spacing.spaceSmall))

            HeaderItem(text = "Язык")
            PlainItem(
                text = "Родной язык",
                onClick = {
                    //todo open select language bottomDrawer
                }
            )
            SwitcherItem(
                text = "С родного на иностранный",
                isChecked = true,
                onCheckedChanged = {
                    Log.d(TAG_SWITCHER, "С родного на иностр = $it")
                }
            )
            Divider(
                Modifier.padding(
                    horizontal = spacing.spaceMedium,
                    vertical = spacing.spaceSmall
                )
            )

            HeaderItem(text = "Внешний вид")
            SwitcherItem(
                text = "Темная тема",
                isChecked = state.mode == MODE_DARK,
                onCheckedChanged = {
                    viewModel.onEvent(SettingsEvent.OnModeChanged(if (it) MODE_DARK else MODE_LIGHT))
                },
                isEnabled = !state.followSystemMode
            )
            SwitcherItem(
                text = "Использовать тему телефона",
                isChecked = state.followSystemMode,
                onCheckedChanged = {
                    viewModel.onEvent(SettingsEvent.OnFollowSystemModeChanged(it))
                }
            )
            Divider(
                Modifier.padding(
                    horizontal = spacing.spaceMedium,
                    vertical = spacing.spaceSmall
                )
            )

//            HeaderItem(text = "Напоминание")
            //todo ExpandableItem
        }
    }
}
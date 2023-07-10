package space.rodionov.porosenokpetr.feature_settings.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.presentation.components.ChoiceItem
import space.rodionov.porosenokpetr.core.presentation.components.TopBar
import space.rodionov.porosenokpetr.core.util.Constants.MODE_DARK
import space.rodionov.porosenokpetr.core.util.Constants.MODE_LIGHT
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.core.util.UiEffect
import space.rodionov.porosenokpetr.feature_settings.presentation.components.HeaderItem
import space.rodionov.porosenokpetr.feature_settings.presentation.components.SettingsBottomDrawer
import space.rodionov.porosenokpetr.feature_settings.presentation.components.SwitcherItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    state: SettingsState,
    uiEffect: Flow<UiEffect>,
    onEvent: (SettingsEvent) -> Unit
) {

    val spacing = LocalSpacing.current
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
        uiEffect.collectLatest { effect ->
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

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        sheetContent = {

            SettingsBottomDrawer(
                //todo check languages available
                languages = listOf(Language.Russian, Language.Ukrainian, Language.English),
                onLanguageClick = {
                    onEvent(SettingsEvent.OnNativeLanguageChanged(it))
                    scope.launch { sheetState.hide() }
                }
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
                hasMenuIcon = false,
                onBackClick = {
                    onEvent(SettingsEvent.OnBackClick)
                },
                onMenuClick = {}
            )

            Divider(Modifier.padding(bottom = spacing.spaceSmall))

            HeaderItem(text = "Язык")
            ChoiceItem(
                textDesc = stringResource(id = R.string.native_language_settings),
                textChoice = stringResource(id = state.nativeLanguage.languageNameRes),
                onClick = {
                    scope.launch { sheetState.show() }
                }
            )
            SwitcherItem(
                text = stringResource(id = R.string.native_to_foreign),
                isChecked = state.isNativeToForeign,
                onCheckedChanged = {
                    onEvent(SettingsEvent.OnTranslationDirectionChanged(it))
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
                    onEvent(SettingsEvent.OnModeChanged(if (it) MODE_DARK else MODE_LIGHT))
                },
                isEnabled = !state.isFollowingSystemMode
            )
            SwitcherItem(
                text = "Использовать тему телефона",
                isChecked = state.isFollowingSystemMode,
                onCheckedChanged = {
                    onEvent(SettingsEvent.OnFollowSystemModeChanged(it))
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

@Preview
@Composable
fun SettingsScreenPreview(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    // Create a preview instance of your screen using the provided scaffoldState and viewModel
    SettingsScreen(
        onNavigateUp = {},
        scaffoldState = scaffoldState,
        SettingsState(),
        emptyFlow(),
        { }
    )
}
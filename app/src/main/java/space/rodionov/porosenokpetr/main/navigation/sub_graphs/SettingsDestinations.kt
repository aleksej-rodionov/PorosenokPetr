package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.core.util.daggerComposeViewModel
import space.rodionov.porosenokpetr.feature_settings.di.DaggerSettingsComponent
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsMainScreen
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

fun NavGraphBuilder.addSettingsGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {

    this.composable(route = SettingsDestinations.SettingsMain.route) {

        val component = DaggerSettingsComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()

        val viewModel: SettingsViewModel = daggerComposeViewModel {
            component.getSettingsViewModel()
        }

        SettingsMainScreen(
            onNavigateUp = {
                navController.navigateUp()
            },
            scaffoldState,
            viewModel.state,
            viewModel.uiEffect,
            { viewModel.onEvent(it) }
        )
    }
}

sealed class SettingsDestinations(val route: String) {

    object SettingsMain: SettingsDestinations(route = "settingsMain")
}

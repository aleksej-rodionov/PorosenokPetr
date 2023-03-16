package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsMainScreen

fun NavGraphBuilder.addSettingsGraph(
    navHostController: NavHostController
) {

    this.composable(route = SettingsDestinations.SettingsMain.route) {
        SettingsMainScreen()
    }
}

sealed class SettingsDestinations(val route: String) {

    object SettingsMain: SettingsDestinations(route = "settingsMain")
}

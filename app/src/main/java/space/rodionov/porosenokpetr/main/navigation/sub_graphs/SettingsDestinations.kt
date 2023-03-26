package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsMainScreen

fun NavGraphBuilder.addSettingsGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModelOwner: ComponentActivity,
    viewModelFactory: ViewModelFactory
) {

    this.composable(route = SettingsDestinations.SettingsMain.route) {
        SettingsMainScreen(
            onNavigateUp = {
                navController.navigateUp()
            },
            scaffoldState,
            viewModelOwner,
            viewModelFactory
        )
    }
}

sealed class SettingsDestinations(val route: String) {

    object SettingsMain: SettingsDestinations(route = "settingsMain")
}

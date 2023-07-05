package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.core.util.daggerComposeViewModel
import space.rodionov.porosenokpetr.feature_settings.di.DaggerSettingsComponent
import space.rodionov.porosenokpetr.feature_settings.di.SettingsComponent
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsMainScreen
import space.rodionov.porosenokpetr.feature_settings.presentation.SettingsViewModel
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

fun NavGraphBuilder.addSettingsGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
//    viewModelOwner: ComponentActivity,
//    viewModelFactory: ViewModelFactory
) {

    this.composable(route = SettingsDestinations.SettingsMain.route) {

        val component = DaggerSettingsComponent
            .builder()
            .appComponent(PorosenokPetrApp.component ?: throw Exception("The fucking AppComponent is not found to inject SettingsComponent =("))
            .build()

        val viewModel: SettingsViewModel = daggerComposeViewModel {
            component.getViewModel()
        }

        SettingsMainScreen(
            onNavigateUp = {
                navController.navigateUp()
            },
            scaffoldState,
            viewModel
//            viewModelOwner,
//            viewModelFactory
        )
    }
}

sealed class SettingsDestinations(val route: String) {

    object SettingsMain: SettingsDestinations(route = "settingsMain")
}

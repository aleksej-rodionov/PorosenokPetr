package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import space.rodionov.porosenokpetr.core.util.daggerComposeViewModel
import space.rodionov.porosenokpetr.feature_vocabulary.di.DaggerVocabularyComponent
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyMainScreen
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularySearchScreen
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyViewModel
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

fun NavGraphBuilder.addVocabularyGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {

    this.composable(route = VocabularyDestinations.VocabularyMain.route) {

        val component = DaggerVocabularyComponent
            .builder()
            .appComponent(
                PorosenokPetrApp.component
                    ?: throw Exception("The AppComponent is not found to inject SettingsComponent =(")
            )
            .build()

        val viewModel: VocabularyViewModel = daggerComposeViewModel {
            component.getVocabularyViewModel()
        }

        VocabularyMainScreen(
            onNavigateUp = {
                navController.navigateUp()
            },
            scaffoldState,
            viewModel.state,
            viewModel.uiEffect,
            { viewModel.onEvent(it) }
        )
    }

    this.composable(
        route = VocabularyDestinations.VocabularySearch.routeWithArgs,
        arguments = VocabularyDestinations.VocabularySearch.arguments
    ) {
        VocabularySearchScreen()
    }
}

sealed class VocabularyDestinations(val route: String) {

    object VocabularyMain : VocabularyDestinations(route = "vocabularyMain")

    object VocabularySearch : VocabularyDestinations(route = "vocabularySearch") {
        val routeWithArgs = "$route/{name}"
        val arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
                defaultValue = "unknown"
            }
        )
    }
}

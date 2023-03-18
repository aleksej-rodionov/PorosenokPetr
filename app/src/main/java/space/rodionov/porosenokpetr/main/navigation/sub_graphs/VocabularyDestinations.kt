package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyMainScreen
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularySearchScreen

fun NavGraphBuilder.addVocabularyGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModelOwner: ComponentActivity,
    viewModelFactory: ViewModelFactory
) {

    this.composable(route = VocabularyDestinations.VocabularyMain.route) {
        VocabularyMainScreen(
            onNavigateUp = {
                navController.navigateUp()
            },
            scaffoldState,
            viewModelOwner,
            viewModelFactory
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

    object VocabularyMain: VocabularyDestinations(route = "vocabularyMain")

    object VocabularySearch: VocabularyDestinations(route = "vocabularySearch") {
        val routeWithArgs = "$route/{name}"
        val arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
                defaultValue = "unknown"
            }
        )
    }
}

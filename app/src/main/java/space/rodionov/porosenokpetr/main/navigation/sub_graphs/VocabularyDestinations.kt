package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import space.rodionov.porosenokpetr.core.util.daggerComposeViewModel
import space.rodionov.porosenokpetr.feature_vocabulary.di.DaggerVocabularyComponent
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyScreen
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.VocabularyViewModel
import space.rodionov.porosenokpetr.feature_wordeditor.di.DaggerWordEditorComponent
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.WordEditorScreen
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.WordEditorViewModel
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

fun NavGraphBuilder.addVocabularyGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {

    this.composable(route = VocabularyDestinations.WordCollection.route) {

        val component = DaggerVocabularyComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()

        val viewModel: VocabularyViewModel = daggerComposeViewModel {
            component.getVocabularyViewModel()
        }

        VocabularyScreen(
            onNavigateTo = {
                navController.navigate(it)
            },
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
        route = VocabularyDestinations.WordEditor.routeWithArgs,
        arguments = VocabularyDestinations.WordEditor.arguments
    ) {

        val component = DaggerWordEditorComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()

        val viewModel: WordEditorViewModel = daggerComposeViewModel {
            component.getWordEditorViewModel()
        }

        WordEditorScreen(
            onNavigateUp = { navController.navigateUp() },
            scaffoldState,
            viewModel.state,
            viewModel.uiEffect,
            { viewModel.onEvent(it) }
        )
    }
}

sealed class VocabularyDestinations(val route: String) {

    object WordCollection : VocabularyDestinations(route = "wordCollection")

    object WordEditor : VocabularyDestinations(route = "wordEditor") {
        val routeWithArgs = "$route/{name}"
        val arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
                defaultValue = "unknown"
            }
        )
    }
}

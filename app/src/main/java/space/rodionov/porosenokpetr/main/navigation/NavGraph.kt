package space.rodionov.porosenokpetr.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import space.rodionov.porosenokpetr.main.navigation.sub_graphs.*

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ChildGraphs.CardStackGraph.route
) {

    NavHost(
        navController =navController,
        startDestination =startDestination,
        modifier = modifier,
        builder = {

            navigation(
                route = ChildGraphs.CardStackGraph.route,
                startDestination = CardStackDestinations.CardStackMain.route
            ) {
                addCardStackGraph(navController)
            }

            navigation(
                route = ChildGraphs.VocabularyGraph.route,
                startDestination = VocabularyDestinations.VocabularyMain.route
            ) {
                addVocabularyGraph(navController)
            }

            navigation(
                route = ChildGraphs.SettingsGraph.route,
                startDestination = SettingsDestinations.SettingsMain.route
            ) {
                addSettingsGraph(navController)
            }
        }
    )
}

sealed class ChildGraphs(
    val route: String,
    val icon: ImageVector
) {

    object CardStackGraph : ChildGraphs(
        route = "cardStackGraph",
        icon = Icons.Filled.ThumbUp
    )

    object VocabularyGraph : ChildGraphs(
        route = "vocabularyGraph",
        icon = Icons.Filled.ThumbUp
    )

    object SettingsGraph : ChildGraphs(
        route = "settingsGraph",
        icon = Icons.Filled.ThumbUp
    )
}
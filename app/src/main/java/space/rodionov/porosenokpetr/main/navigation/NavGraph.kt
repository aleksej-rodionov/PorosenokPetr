package space.rodionov.porosenokpetr.main.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.main.navigation.sub_graphs.*

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState,
    startDestination: String = ChildGraphs.CardStackGraph.route,
    viewModelOwner: ComponentActivity,
    viewModelFactory: ViewModelFactory
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
                addCardStackGraph(navController, scaffoldState, viewModelOwner, viewModelFactory)
            }

            navigation(
                route = ChildGraphs.VocabularyGraph.route,
                startDestination = VocabularyDestinations.VocabularyMain.route
            ) {
                addVocabularyGraph(navController, scaffoldState, viewModelOwner, viewModelFactory)
            }

            navigation(
                route = ChildGraphs.SettingsGraph.route,
                startDestination = SettingsDestinations.SettingsMain.route
            ) {
                addSettingsGraph(navController, scaffoldState, viewModelOwner, viewModelFactory)
            }
        }
    )
}

sealed class ChildGraphs(
    val route: String,
    val icon: Int
) {

    object CardStackGraph : ChildGraphs(
        route = "cardStack",
        icon = R.drawable.ic_study
    )

    object VocabularyGraph : ChildGraphs(
        route = "vocabulary",
        icon = R.drawable.ic_bookstack
    )

    object SettingsGraph : ChildGraphs(
        route = "settings",
        icon = R.drawable.ic_settings_big
    )
}
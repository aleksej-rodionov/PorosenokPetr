package space.rodionov.porosenokpetr.main.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import space.rodionov.porosenokpetr.main.navigation.sub_graphs.CardStackDestinations
import space.rodionov.porosenokpetr.main.navigation.sub_graphs.SettingsDestinations
import space.rodionov.porosenokpetr.main.navigation.sub_graphs.VocabularyDestinations
import space.rodionov.porosenokpetr.ui.theme.Gray850

const val TAG_NAV = "TAG_NAV"

@Composable
fun CustomBottomNavigation(navController: NavController) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute =
        currentBackStack?.destination?.route ?: ChildGraphs.CardStackGraph.route

    val bottomDestinations = listOf(
        ChildGraphs.CardStackGraph,
        ChildGraphs.VocabularyGraph,
        ChildGraphs.SettingsGraph
    )

    BottomNavigation {

        bottomDestinations.forEach { bottomDest ->

            val graphRoute = bottomDest.route

            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = bottomDest.icon, contentDescription = bottomDest.route)
                    }
                },
                selected = currentRoute == graphRoute,
//                selected = currentRoute.isChildOf(graphRoute),
                selectedContentColor = Gray850,
                unselectedContentColor = Gray850.copy(alpha = 0.4f),
                alwaysShowLabel = true,
                onClick = {
                    if (currentRoute.startsWith(graphRoute)) { // if we already inside this subGraph..
                        navController.navigate(
                            route = findGraphRootRoute(graphRoute),
                            builder = {
                                popUpTo(findStartDestination(navController.graph).id)
                            }
                        )
                    } else if (graphRoute != currentRoute) { // does it make sense at all if apper case is not true???!
                        Log.d(TAG_NAV, "CustomBottomNavigation: STRANGE CONDITION REACHED")
                        navController.navigate(
                            route = graphRoute,
                            builder = {
                                launchSingleTop = true
                                restoreState = true
                                // Pop up backstack to the first destination and save state. This makes going back
                                // to the start destination when pressing back in any other bottom tab.
                                //todo comment the code below if you use "isChildOf()" for "selected = " parameter
                                val startDest = findStartDestination(navController.graph)
                                popUpTo(startDest.id) {
                                    saveState = true
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private fun findGraphRootRoute(graphRoute: String): String {
    return when (graphRoute) {
        ChildGraphs.CardStackGraph.route -> CardStackDestinations.CardStackMain.route
        ChildGraphs.VocabularyGraph.route -> VocabularyDestinations.VocabularyMain.route
        ChildGraphs.SettingsGraph.route -> SettingsDestinations.SettingsMain.route
        else -> ChildGraphs.CardStackGraph.route
    }
}

private fun String.isChildOf(parentRoute: String): Boolean {
    return this.startsWith(parentRoute)
}




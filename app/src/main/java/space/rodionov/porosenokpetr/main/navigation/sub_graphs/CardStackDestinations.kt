package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackMainScreen

fun NavGraphBuilder.addCardStackGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModelOwner: ComponentActivity,
    viewModelFactory: ViewModelFactory
) {

    this.composable(route = CardStackDestinations.CardStackMain.route) {
        CardStackMainScreen(
            scaffoldState,
            viewModelOwner,
            viewModelFactory
        )
    }
}

sealed class CardStackDestinations(val route: String) {

    object CardStackMain: CardStackDestinations(route = "cardStackMain")
}
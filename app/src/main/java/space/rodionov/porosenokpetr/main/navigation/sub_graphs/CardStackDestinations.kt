package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackMainScreen

fun NavGraphBuilder.addCardStackGraph(
    navController: NavHostController
) {

    this.composable(route = CardStackDestinations.CardStackMain.route) {
        CardStackMainScreen()
    }
}

sealed class CardStackDestinations(val route: String) {

    object CardStackMain: CardStackDestinations(route = "cardStackMain")
}
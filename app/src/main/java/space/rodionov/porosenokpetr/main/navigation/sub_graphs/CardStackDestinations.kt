package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.core.util.daggerComposeViewModel
import space.rodionov.porosenokpetr.feature_cardstack.di.DaggerCardStackComponent
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackMainScreen
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackViewModel
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

fun NavGraphBuilder.addCardStackGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {

    this.composable(route = CardStackDestinations.CardStackMain.route) {

        val component = DaggerCardStackComponent
            .builder()
            .appComponent(PorosenokPetrApp.component!!)
            .build()

        val viewModel: CardStackViewModel = daggerComposeViewModel {
            component.getCardStackViewModel()
        }

        CardStackMainScreen(
            navigateTo = { navController.navigate(it) },
            scaffoldState,
            viewModel.state,
            viewModel.uiEffect,
            { viewModel.onEvent(it) }
        )
    }
}

sealed class CardStackDestinations(val route: String) {

    object CardStackMain : CardStackDestinations(route = "cardStackMain")
}
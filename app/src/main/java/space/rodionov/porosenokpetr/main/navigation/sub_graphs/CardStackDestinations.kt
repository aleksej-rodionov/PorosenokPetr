package space.rodionov.porosenokpetr.main.navigation.sub_graphs

import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import space.rodionov.porosenokpetr.core.util.ViewModelFactory
import space.rodionov.porosenokpetr.core.util.daggerComposeViewModel
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackMainScreen
import space.rodionov.porosenokpetr.feature_cardstack.presentation.CardStackViewModel
import space.rodionov.porosenokpetr.main.PorosenokPetrApp

fun NavGraphBuilder.addCardStackGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModelOwner: ComponentActivity,
    viewModelFactory: ViewModelFactory
) {

    this.composable(route = CardStackDestinations.CardStackMain.route) {

        val component = DaggerCardStackComponent
            .builder
            .appComponent(PorosenokPetrApp.component ?: throw Exception("The AppComponent is not found to inject CardStackComponent =("))
            .build()

        val viewModel: CardStackViewModel = daggerComposeViewModel {
            component.getCardStackViewModel()
        }

        CardStackMainScreen(
            scaffoldState,
            viewModel
        )
    }
}

sealed class CardStackDestinations(val route: String) {

    object CardStackMain: CardStackDestinations(route = "cardStackMain")
}
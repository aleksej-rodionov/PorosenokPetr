package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import space.rodionov.porosenokpetr.MainViewModel

//@ExperimentalAnimationApi
@Composable
fun CollectionScreen(
    navController: NavController,
    vmCollection: CollectionViewModelNew = hiltViewModel(),
    vmMain: MainViewModel = hiltViewModel()
) {
//    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            IconButton(onClick = {
                vmMain.switchUiShit() // todo pass it through viewModel event
//                navController.navigateUp() // todo pass it through viewModel event
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(text = "Коллекция", style = MaterialTheme.typography.h4)
        }
    }
}
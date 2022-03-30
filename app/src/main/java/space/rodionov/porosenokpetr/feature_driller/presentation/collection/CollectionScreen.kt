package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import space.rodionov.porosenokpetr.MainViewModel

//@Composable
//fun CollectionScreen(openOtherScreen: () -> Unit) {

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
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
//                    vmMain.switchUiShit() // todo pass it through viewModel event
                    navController.navigateUp() // todo pass it through viewModel event
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.CenterStart),
                    text = "Коллекция",
                    style = MaterialTheme.typography.h6,
                )
            }
            TextField(
//                value = viewModel.searchQuery.value,
//                onValueChange = viewModel::onSearch,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search...") }
            )
            Divider()
        }
    }
}
package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//@Composable
//fun CollectionScreen(openOtherScreen: () -> Unit) {

//@ExperimentalAnimationApi
//@Destination
@Composable
fun CollectionScreen(
    navController: NavController,
//    vmCollection: CollectionViewModelNew = hiltViewModel(), // todo inject viewModels by Dagger2
//    vmMain: MainViewModel = hiltViewModel() // todo inject viewModels by Dagger2
) {
//    val state = vmCollection.state.value // todo inject viewModels by Dagger2
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
//                    navController.navigateToWordlistScreen() // todo navigate
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
                Text(
//                value = viewModel.searchQuery.value,
//                onValueChange = viewModel::onSearch,
                    modifier = Modifier.fillMaxWidth(),
                    text = "Искать слово ..",
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body1
                )
            }
            Divider()
            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(state.catsWithWords) { cww -> // todo inject viewModels by Dagger2
//                    CollectionItem(
//                        catWithWords = cww,
//                        onChecked = {
//                            if (it) {
//                                vmCollection.activateCategory(cww.category.name)
//                            } else {
//                                if (vmCollection.howManyActiveCats() < 2) {
//                                    vmCollection.updateCatSwitchState(cww.category)
//                                    vmCollection.shoeSnackbar("Нельзя отключить все категории"/*getString(R.string.cannot_turn_all_cats_off)*/)
//                                } else {
//                                    vmCollection.inactivateCategory(cww.category.name)
//                                }
//                            }
//                        }
//                    )
//                }
            }
        }
    }
}
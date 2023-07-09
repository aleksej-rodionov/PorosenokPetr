package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import space.rodionov.porosenokpetr.core.util.UiEffect

@Composable
fun WordEditorScreen(
    onNavigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    state: WordEditorState,
    uiEffect: Flow<UiEffect>,
    onEvent: (WordEditorEvent) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .wrapContentHeight()
        ) {
            TextField(
                value = "text1",
                onValueChange = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(4.dp),
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                placeholder = {
                    Text("Hint 1", fontSize = 12.sp)
                              },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            TextField(
                value = "text2",
                onValueChange = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(4.dp),
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                placeholder = {
                    Text("Hint 2", fontSize = 12.sp)
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun WordEditorScreenPreview(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    WordEditorScreen(
        onNavigateUp = {},
        scaffoldState = scaffoldState,
        state = WordEditorState(),
        uiEffect = emptyFlow(),
        onEvent = {}
    )
}
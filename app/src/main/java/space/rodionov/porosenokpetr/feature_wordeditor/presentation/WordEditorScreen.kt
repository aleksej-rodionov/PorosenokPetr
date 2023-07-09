package space.rodionov.porosenokpetr.feature_wordeditor.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.flow.Flow
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
        Text(
            style = MaterialTheme.typography.h4,
            text = "Word Editor Screen",
            fontWeight = FontWeight.Bold
        )
    }
}
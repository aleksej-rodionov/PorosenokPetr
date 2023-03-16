package space.rodionov.porosenokpetr.feature_vocabulary.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.core.domain.model.Category

@Composable
fun VocabularyMainScreen(
    onSearch: (category: Category?) -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            style = MaterialTheme.typography.h4,
            text = "Vocabulary Main Screen",
            fontWeight = FontWeight.Bold
        )
    }
}
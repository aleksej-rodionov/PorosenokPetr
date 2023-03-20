package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem

@Composable
fun DropWordProgressDialog(
    word: VocabularyItem.WordUi,
    onConfirmClick: (VocabularyItem.WordUi) -> Unit,
    onDissmiss: () -> Unit
) {

    AlertDialog(
        title = { Text(text = "Drop word proggress?") },
        onDismissRequest = { onDissmiss() },
        confirmButton = {
            TextButton(onClick = { onConfirmClick(word) }) {
                Text(text = "Drop")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDissmiss() }) {
                Text(text = "No")
            }
        }
    )
}
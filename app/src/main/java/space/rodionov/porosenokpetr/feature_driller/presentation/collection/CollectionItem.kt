package space.rodionov.porosenokpetr.feature_driller.presentation.collection

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import space.rodionov.porosenokpetr.feature_driller.domain.models.CatWithWords

@Composable
fun CollectionItem(
    catWithWords: CatWithWords,
    modifier: Modifier = Modifier,
    onChecked: (isChecked: Boolean) -> Unit
) {
    Row(modifier = Modifier) {
        Text(
            text = catWithWords.category.nameEng ?: "",
            style = MaterialTheme.typography.body1
        )
        Switch(checked = false, onCheckedChange = {
            onChecked(it)
        })
    }
}

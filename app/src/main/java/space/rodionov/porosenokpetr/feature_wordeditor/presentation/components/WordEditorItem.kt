package space.rodionov.porosenokpetr.feature_wordeditor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.Translation

@Composable
fun WordEditorItem(
    translation: Translation,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = translation.translation,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(0.dp),
        label = {
            Text(translation.language.toString(), fontSize = 12.sp)
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun WordEditorItemPreview() {
    WordEditorItem(
        translation = Translation(Language.Swedish,"Test value"),
        onValueChange = {}
    )
}
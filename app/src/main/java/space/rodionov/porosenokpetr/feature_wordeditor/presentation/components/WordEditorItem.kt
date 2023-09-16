package space.rodionov.porosenokpetr.feature_wordeditor.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.Language
import space.rodionov.porosenokpetr.feature_wordeditor.presentation.model.Translation
import space.rodionov.porosenokpetr.ui.theme.Gray600

@Composable
fun WordEditorItem(
    translation: Translation,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    onIconClick: () -> Unit = {},
    imageVector: ImageVector? = null,
    @DrawableRes drawableRes: Int? = null
) {

    val spacing = LocalSpacing.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {

        OutlinedTextField(
            value = translation.translation,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent)
                .padding(0.dp),
            label = {
                Text(stringResource(id = translation.language.languageNameRes), fontSize = 12.sp)
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                    defaultKeyboardAction(ImeAction.Done)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        if (imageVector != null) {
            IconButton(
                onClick = { onIconClick() }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Icon",
                    modifier = Modifier.padding(spacing.spaceMedium),
                    tint = Gray600
                )
            }
        } else if (drawableRes != null) {
            IconButton(
                onClick = { onIconClick() }) {
                Icon(
                    painter = painterResource(id = drawableRes),
                    contentDescription = "Icon",
                    tint = Gray600,
                    modifier = Modifier.padding(spacing.spaceMedium)
                )
            }
        }
    }
}

@Preview
@Composable
fun WordEditorItemPreview() {
    WordEditorItem(
        translation = Translation(Language.Swedish, "Test value"),
        onValueChange = {},
        onDone = {}
    )
}
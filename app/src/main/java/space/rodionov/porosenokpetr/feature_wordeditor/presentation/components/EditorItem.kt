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
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.core.util.Language

@Composable
fun EditorItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    onIconClick: () -> Unit = {},
    imageVector: ImageVector? = null,
    @DrawableRes drawableRes: Int? = null,
    singleLine: Boolean = true,
    capitalize: Boolean = false
) {

    val spacing = LocalSpacing.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(0.dp)
                .weight(1f),
            label = {
                Text(label, fontSize = 12.sp)
            },
            singleLine = singleLine,
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
                imeAction = ImeAction.Done,
                capitalization = if (capitalize) KeyboardCapitalization.Sentences
                else KeyboardCapitalization.None
            )
        )

        if (imageVector != null) {
            IconButton(
                onClick = { onIconClick() }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Icon",
                    modifier = Modifier.padding(spacing.spaceMedium)
                )
            }
        } else if (drawableRes != null) {
            IconButton(
                onClick = { onIconClick() }) {
                Icon(
                    painter = painterResource(id = drawableRes),
                    contentDescription = "Icon",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(spacing.spaceMedium)
                )
            }
        }
    }
}

@Preview
@Composable
fun EditorItemPreview() {
    EditorItem(
        value = "Test value",
        label = stringResource(id = Language.Swedish.languageNameRes),
        onValueChange = {},
        onDone = {}
    )
}
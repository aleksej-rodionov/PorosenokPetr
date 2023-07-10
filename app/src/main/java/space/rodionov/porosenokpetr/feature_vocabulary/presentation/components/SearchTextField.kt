package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import space.rodionov.porosenokpetr.R
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.ui.theme.*

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    query: String,
    shouldShowHint: Boolean = false,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(id = R.string.search_word),
    onFocusChanged: (FocusState) -> Unit
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(spacing.spaceSmall))
            .background(color = TransparentGray15)
            .padding(start = spacing.spaceExtraSmall)
    ) {

        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = spacing.spaceSmall),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.search_word),
            tint = MaterialTheme.colors.onBackground
        )

        Box(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceExtraSmall)
                    .height(28.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .onFocusChanged { onFocusChanged(it) },
                value = query,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onBackground
                ),
                cursorBrush = SolidColor(Gray600),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                )
            )

            if (shouldShowHint) {

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = spacing.spaceMedium),
                    text = hint,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Light,
                    color = Gray600
                )
            }
        }
    }
}

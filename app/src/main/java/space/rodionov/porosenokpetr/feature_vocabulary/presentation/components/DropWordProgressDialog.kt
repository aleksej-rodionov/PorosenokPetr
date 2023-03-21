package space.rodionov.porosenokpetr.feature_vocabulary.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import space.rodionov.porosenokpetr.core.presentation.LocalSpacing
import space.rodionov.porosenokpetr.feature_vocabulary.presentation.model.VocabularyItem
import space.rodionov.porosenokpetr.ui.theme.Gray100
import space.rodionov.porosenokpetr.ui.theme.Gray900

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DropWordProgressDialog(
    wordToAsk: VocabularyItem.WordUi,
    onConfirmClick: (VocabularyItem.WordUi) -> Unit,
    onDissmiss: () -> Unit
) {

    val spacing = LocalSpacing.current
    val word = remember { mutableStateOf(wordToAsk) }

    Dialog(
        onDismissRequest = { onDissmiss() }
    ) {

        Surface(
            modifier = Modifier.padding(spacing.spaceLarge),
            shape = RoundedCornerShape(spacing.spaceMedium),
            color = Gray100
        ) {

            Box(
                modifier = Modifier.padding(spacing.spaceSmall),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier.padding(spacing.spaceMedium)
                ) {

                    Text(
                        text = "Drop word proggress?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceLarge))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Button(
                            onClick = { onConfirmClick(word.value) }
                        ) {
                            Text(
                                text = "Yes",
                                color = Gray900
                            )
                        }

                        Button(
                            onClick = { onDissmiss() }
                        ) {
                            Text(
                                text = "No",
                                color = Gray900
                            )
                        }
                    }
                }
            }
        }
    }
}
package com.pokemon.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pokemon.ui.theme.PokemonTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Surface {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        "Error",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.Red
                        )
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(message, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    )
}

@Preview
@Composable
private fun DialogPreview() {
    PokemonTheme {
        ErrorDialog(
            message = "Error dialog message content, it probably be a paragraph of more than 4 lines",
            onDismiss = {}
        )
    }
}
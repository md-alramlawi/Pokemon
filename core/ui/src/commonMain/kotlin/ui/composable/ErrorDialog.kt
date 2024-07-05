package ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.MediumRoundedCornerShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppErrorDialog(
    message: String, onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        content = {
            Surface(shape = MediumRoundedCornerShape) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Text(
                        "Error",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.Red
                        )
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(message, style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(20.dp))
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.End)
                    ){
                        Text("Ok")
                    }
                }
            }
        }
    )
}
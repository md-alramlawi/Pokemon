package pokemon.feature.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.composable.AppImage
import ui.composable.BookmarkIconButton
import ui.theme.MediumRoundedCornerShape

@Composable
fun PokemonItem(
    id: String,
    name: String,
    iconUrl: String,
    onClick: () -> Unit,
    onClickSave: () -> Unit,
    isBookmarked: Boolean
) {
    Surface(
        shape = MediumRoundedCornerShape
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth().padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BookmarkIconButton(isBookmarked){
                    onClickSave()
                }

                Text(
                    text = "#$id",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            AppImage(
                modifier = Modifier.size(100.dp),
                imageUrl = iconUrl,
                contentDescription = name,
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.height(40.dp),
                text = name,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
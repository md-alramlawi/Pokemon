package pokemon.feature.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pokemon.ui.composable.AppImage

@Composable
internal fun PokemonItem(
    id: String,
    name: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "#$id",
                style = MaterialTheme.typography.labelMedium
            )
            AppImage(
                modifier = Modifier.height(100.dp).fillMaxWidth(),
                imageUrl = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.FillWidth
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
    }
}
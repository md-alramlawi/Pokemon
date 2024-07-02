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
import com.pokemon.model.SimplePokemon
import com.pokemon.ui.composable.AppImage

@Composable
internal fun PokemonItem(pokemon: SimplePokemon, onClick: (SimplePokemon) -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.clickable { onClick(pokemon) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "#${pokemon.id}",
                style = MaterialTheme.typography.labelMedium
            )
            AppImage(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = pokemon.url,
                contentDescription = pokemon.name,
                contentScale = ContentScale.FillWidth
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
    }
}
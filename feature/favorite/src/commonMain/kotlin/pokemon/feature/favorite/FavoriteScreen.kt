package pokemon.feature.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.SimplePokemon
import org.koin.compose.koinInject
import pokemon.feature.home.composable.PokemonItem
import ui.composable.AppErrorDialog
import ui.composable.AppIconButton
import ui.painter.backPainter
import ui.state.UIEvent
import ui.theme.Gray95

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = koinInject(),
    onClickItem: () -> Unit,
    onBack: () -> Unit
) {

    val list by viewModel.list.collectAsState()
    val uiEvent by viewModel.uiEvents.collectAsState()


    FavoriteContent(
        list = list,
        onClickItem = {
            viewModel.setCurrent(it.name)
            onClickItem()
        },
        onBack = onBack,
        onClickSave = viewModel::bookmark
    )

    when (uiEvent) {
        is UIEvent.Error -> {
            AppErrorDialog((uiEvent as UIEvent.Error).message) { viewModel.onReleaseScreenState() }
        }

        else -> {}
    }
}


@Composable
private fun FavoriteContent(
    list: List<SimplePokemon>,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Gray95)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Red)
                .padding(10.dp)
                .windowInsetsPadding(WindowInsets.statusBars),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton { onBack() }
            Spacer(Modifier.width(20.dp))
            Text(
                text = "Favorite",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(
                horizontal = 5.dp,
                vertical = 10.dp
            )
        ) {
            items(list, key = { it.name }) { pokemon ->
                PokemonItem(
                    id = pokemon.id,
                    name = pokemon.name,
                    imageUrl = pokemon.url,
                    isFavorite = true,
                    onClick = { onClickItem(pokemon) },
                    onClickSave = { onClickSave(pokemon) }
                )
            }
        }
    }
}

@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppIconButton(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.1f)),
        painter = backPainter(),
        tint = Color.White,
        onClick = onClick
    )
}
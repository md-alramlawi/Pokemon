package feature.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.composable.AppBarHeight
import core.ui.composable.AppIconButton
import core.ui.composable.AppTopBar
import core.ui.painter.backPainter
import feature.home.composable.PokemonItem
import model.SimplePokemon
import org.koin.compose.koinInject

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = koinInject(),
    onClickItem: () -> Unit,
    onBack: () -> Unit
) {

    val list by viewModel.list.collectAsStateWithLifecycle()
//    val uiEvent by viewModel.uiEvents.collectAsState()


    FavoriteContent(
        list = list,
        onClickItem = { viewModel.setCurrent(it.name);onClickItem() },
        onBack = onBack,
        onClickSave = viewModel::bookmark
    )

//    when (uiEvent) {
//        is UIEvent.Error -> {
//            ErrorDialog((uiEvent as UIEvent.Error).message) { viewModel.onReleaseScreenState() }
//        }
//
//        else -> {}
//    }
}


@Composable
private fun FavoriteContent(
    list: List<SimplePokemon>,
    onClickItem: (SimplePokemon) -> Unit,
    onClickSave: (SimplePokemon) -> Unit,
    onBack: () -> Unit
) {
    Box {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(
                start = 5.dp,
                end = 5.dp,
                top = AppBarHeight.BasicHeight,
                bottom = 20.dp
            )
        ) {
            items(list, key = { it.name }) { pokemon ->
                PokemonItem(
                    id = pokemon.id,
                    name = pokemon.name,
                    iconUrl = pokemon.url,
                    isBookmarked = true,
                    onClick = { onClickItem(pokemon) },
                    onClickSave = { onClickSave(pokemon) }
                )
            }
        }

        AppTopBar(Modifier.height(AppBarHeight.BasicHeight)) {
            BackButton { onBack() }
            Text(
                text = "Favorite",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BackButton(
    tint: Color = Color.White,
    backgroundColor: Color = Color.Black.copy(alpha = 0.1f),
    onClick: () -> Unit
) {
    AppIconButton(
        modifier = Modifier.clip(CircleShape).background(backgroundColor),
        painter = backPainter(),
        tint = tint,
        onClick = onClick
    )
}
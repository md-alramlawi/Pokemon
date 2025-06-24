package feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Pokemon
import feature.detail.component.BaseStatuses
import feature.detail.component.CompactPokemonInfo
import feature.detail.component.ExpandedPokemonInfo
import feature.detail.component.ImagePager
import org.koin.compose.viewmodel.koinViewModel
import core.ui.brush.shadowBrush
import core.ui.composable.AdaptiveLayout
import core.ui.composable.AppIconButton
import core.ui.composable.ShimmerEffect
import core.ui.painter.BackgroundsPainterMap
import core.ui.painter.backPainter
import core.ui.theme.AppShape

@Composable
fun DetailsScreen(
    viewModel: DetailViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val pokemonDate by viewModel.data.collectAsState()
    val bookmarkIds by viewModel.bookmarkIds.collectAsState()
//    val uiEvent by viewModel.uiEvents.collectAsState()

    PokemonDetailsContent(
        isBookmarked = bookmarkIds.contains(pokemonDate?.id ?: false),
        pokemon = pokemonDate,
        onClickSave = viewModel::bookmark,
        onBack = onBack
    )

//    if (uiEvent is UIEvent.Error) {
//        ErrorDialog((uiEvent as UIEvent.Error).message) { viewModel.onAction(UserAction.Release) }
//    }
}

@Composable
private fun PokemonDetailsContent(
    pokemon: Pokemon?,
    isBookmarked: Boolean,
    onClickSave: (Pokemon) -> Unit,
    onBack: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box {
            pokemon?.let {
                DataContent(
                    pokemon = pokemon,
                    isBookmarked = isBookmarked,
                    onClickSave = { onClickSave(it) }
                )
            } ?: LoadingContent()

            // status background
            AdaptiveLayout(
                compactContent = {
                    StatusBackground(startColor = Color.Gray)
                },
                expandedContent = {}
            )
            BackButton { onBack() }
        }
    }
}


@Composable
private fun DataContent(
    pokemon: Pokemon,
    isBookmarked: Boolean,
    onClickSave: () -> Unit
) {
    AdaptiveLayout(
        compactContent = {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ImagePager(
                    modifier = Modifier
                        .clip(AppShape.BottomOnlyRoundedShape)
                        .weight(1f),
                    images = pokemon.images,
                    backgroundPainter = BackgroundsPainterMap()[pokemon.types.random()]
                )
                Spacer(Modifier.height(10.dp))
                CompactPokemonInfo(
                    modifier = Modifier.weight(1f),
                    name = pokemon.name,
                    height = pokemon.height,
                    weight = pokemon.weight,
                    types = pokemon.types,
                    isBookmarked = isBookmarked,
                    onBookmark = onClickSave,
                )
                Spacer(Modifier.height(10.dp))
                BaseStatuses(
                    modifier = Modifier.weight(1f),
                    stats = pokemon.stats
                )
                Spacer(Modifier.height(20.dp))
            }
        },
        expandedContent = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImagePager(
                    modifier = Modifier
                        .clip(AppShape.EndOnlyRoundedShape)
                        .weight(1.2f),
                    images = pokemon.images,
                    backgroundPainter = BackgroundsPainterMap()[pokemon.types.random()]
                )
                Spacer(Modifier.width(20.dp))
                Column(modifier = Modifier.weight(1.8f)) {
                    ExpandedPokemonInfo(
                        modifier = Modifier.weight(0.8f).fillMaxWidth(),
                        name = pokemon.name,
                        height = pokemon.height,
                        weight = pokemon.weight,
                        types = pokemon.types,
                        isBookmarked = isBookmarked,
                        onBookmark = onClickSave,
                    )
                    BaseStatuses(
                        modifier = Modifier.weight(1.2f).fillMaxWidth(),
                        stats = pokemon.stats
                    )
                }
                Spacer(Modifier.width(20.dp))
            }
        }
    )
}

@Composable
private fun LoadingContent() {
    AdaptiveLayout(
        compactContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ShimmerEffect(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    shape = AppShape.BottomOnlyRoundedShape
                )
                Spacer(Modifier.height(10.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShimmerEffect(modifier = Modifier.height(50.dp).fillMaxWidth(0.8f))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ShimmerEffect(modifier = Modifier.size(height = 50.dp, width = 100.dp))
                        ShimmerEffect(modifier = Modifier.size(height = 50.dp, width = 100.dp))
                    }
                }
                Spacer(Modifier.height(10.dp))
                Column(
                    Modifier.weight(1f).fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                    ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                    ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                    ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                    ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                    ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                }
                Spacer(Modifier.height(20.dp))
            }
        },
        expandedContent = {
            Row {
                ShimmerEffect(
                    modifier = Modifier.padding(end = 15.dp).weight(1.2f).fillMaxHeight(),
                    shape = AppShape.EndOnlyRoundedShape
                )
                Column(
                    modifier = Modifier
                        .weight(1.8f)
                        .fillMaxHeight()
                        .padding(horizontal = 30.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Column {
                        ShimmerEffect(modifier = Modifier.height(50.dp).fillMaxWidth())
                        Spacer(Modifier.height(20.dp))
                        ShimmerEffect(modifier = Modifier.height(30.dp).fillMaxWidth())
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                        ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                        ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                        ShimmerEffect(modifier = Modifier.height(20.dp).fillMaxWidth())
                    }
                }
            }
        }
    )
}


@Composable
private fun BackButton(
    tint: Color = Color.Black,
    backgroundColor: Color = Color.White.copy(alpha = 0.3f),
    onClick: () -> Unit
) {
    AppIconButton(
        modifier = Modifier
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
            .clip(CircleShape)
            .background(backgroundColor),
        painter = backPainter(),
        tint = tint,
        onClick = onClick
    )
}

@Composable
fun StatusBackground(startColor: Color = Color.Gray) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                brush = shadowBrush(
                    listOf(
                        startColor,
                        Color.Transparent
                    )
                )
            )
    )
}
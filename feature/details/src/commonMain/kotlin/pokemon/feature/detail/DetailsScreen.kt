package pokemon.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.Pokemon
import ui.composable.AppIconButton
import ui.composable.AppImage
import ui.composable.AppErrorDialog
import ui.composable.ShimmerEffect
import ui.painter.BackgroundsPainterMap
import ui.painter.backPainter
import ui.state.UIEvent
import ui.theme.roundedBottomShape
import org.koin.compose.koinInject
import pokemon.feature.detail.component.PropertyItem
import pokemon.feature.detail.component.StatusBar
import pokemon.feature.detail.component.TagItem
import pokemon.feature.detail.component.statColors
import ui.state.UserAction

@Composable
fun DetailsScreen(
    viewModel: DetailViewModel = koinInject(),
    onBack: () -> Unit
) {
    val pokemonDate by viewModel.data.collectAsState()
    val uiEvent by viewModel.uiEvents.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getDetails()
    }

    PokemonDetailsContent(pokemonDate, onBack)
    if (uiEvent is UIEvent.Error) {
        AppErrorDialog((uiEvent as UIEvent.Error).message) { viewModel.onAction(UserAction.Release) }
    }
}

@Composable
private fun PokemonDetailsContent(
    pokemon: Pokemon?,
    onBack: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (pokemon == null) {
            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Box {
                        ShimmerEffect(
                            modifier = Modifier.height(300.dp).fillMaxWidth(),
                            shape = roundedBottomShape
                        )
                        BackButton{ onBack() }
                    }
                }

                item {
                    ShimmerEffect(modifier = Modifier.height(50.dp).fillMaxWidth(0.8f))
                }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ShimmerEffect(modifier = Modifier.size(height = 50.dp, width = 100.dp))
                        ShimmerEffect(modifier = Modifier.size(height = 50.dp, width = 100.dp))
                    }
                }

                item {
                    ShimmerEffect(Modifier.padding(top = 10.dp).height(25.dp).fillMaxWidth(0.8f))
                    ShimmerEffect(Modifier.padding(top = 10.dp).height(25.dp).fillMaxWidth(0.8f))
                    ShimmerEffect(Modifier.padding(top = 10.dp).height(25.dp).fillMaxWidth(0.8f))
                    Spacer(Modifier.height(50.dp))
                }
            }
            return@Surface
        }

        LazyColumn(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp)
                        .clip(roundedBottomShape)
                        .background(Color.White),
                ) {
                    BackgroundsPainterMap()[pokemon.types.random()]?.also {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = it,
                            contentDescription = "background",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(color = Color.White.copy(0.75f))
                    )
                    AppImage(
                        imageUrl = pokemon.imageUrl,
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.padding(20.dp).fillMaxSize()
                    )
                    BackButton{ onBack() }
                }
            }

            item {
                Text(
                    pokemon.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    pokemon.types.forEach {
                        TagItem(it.uppercase())
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    PropertyItem(label = "weight", content = "${pokemon.weight} KG")
                    PropertyItem(label = "height", content = "${pokemon.height} CM")
                }
            }

            item {
                Text(
                    text = "Initial Statuses",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
                pokemon.stats.forEachIndexed { index, stat ->
                    StatusBar(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
                            .height(20.dp),
                        label = stat.name,
                        value = stat.baseStat,
                        percentage = stat.percentage,
                        color = statColors[index],
                    )
                    Spacer(Modifier.height(10.dp))
                }
                Spacer(Modifier.height(30.dp))
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
            .padding(10.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.3f)),
        painter = backPainter(),
        tint = Color.Black,
        onClick = onClick
    )
}
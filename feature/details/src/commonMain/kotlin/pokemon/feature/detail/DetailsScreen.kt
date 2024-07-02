package pokemon.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.pokemon.model.Pokemon
import com.pokemon.model.UIEvent
import com.pokemon.ui.composable.AppImage
import com.pokemon.ui.composable.ErrorDialog
import com.pokemon.ui.composable.LoadingOverlay
import com.pokemon.ui.painter.BackgroundsPainterMap
import com.pokemon.ui.painter.backPainter
import com.pokemon.ui.theme.roundedBottomShape
import org.koin.compose.koinInject
import pokemon.feature.detail.component.PropertyItem
import pokemon.feature.detail.component.StatusBar
import pokemon.feature.detail.component.TagItem
import pokemon.feature.detail.component.statColors

@Composable
fun DetailsScreen(
    viewModel: DetailViewModel = koinInject(),
    onBack: () -> Unit
) {
    val pokemonDate by viewModel.data.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getDetails()
    }

    PokemonDetailsContent(pokemonDate, onBack)
    when (uiEvent) {
        is UIEvent.Loading -> {
            LoadingOverlay()
        }

        is UIEvent.Failure -> {
            ErrorDialog((uiEvent as UIEvent.Failure).message) { viewModel.resetUIEvent() }
        }

        else -> {}
    }
}

@Composable
private fun PokemonDetailsContent(pokemonData: Pokemon?, onBack: () -> Unit = {}) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        pokemonData?.also { pokemon ->
            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(270.dp)
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
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .windowInsetsPadding(WindowInsets.statusBars)
                                .padding(horizontal = 20.dp),
                            onClick = onBack
                        ) {
                            Icon(
                                painter = backPainter(),
                                contentDescription = null
                            )
                        }
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
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        PropertyItem(title = "Weight :", content = "${pokemon.weight} KG")
                        Spacer(Modifier.height(8.dp))
                        PropertyItem(title = "Height :", content = "${pokemon.height} CM")
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
                    Spacer(Modifier.height(50.dp))
                }
            }
        }
    }
}
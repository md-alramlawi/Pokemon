package com.pokemon.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.pokemon.model.Pokemon
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data class DetailScreen(val name: String) : Screen {
    @Composable
    override fun Content() {
        val screenModel: DetailScreenModel = getScreenModel()

        val pokemonDate by screenModel.data.collectAsState()
        LaunchedEffect(Unit) {
            screenModel.getDetails(name)
        }

        pokemonDate?.also {
            PokemonDetailsContent(it)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PokemonDetailsContent(pokemon: Pokemon) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        KamelImage(
            resource = asyncPainterResource(data = pokemon.imageUrl),
            contentDescription = pokemon.name,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            PropertyItem(title = "Name :", content = pokemon.name)

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                PropertyItem(title = "Weight :", content = "${pokemon.weight} Kg")
                PropertyItem(title = "Height :", content = "${pokemon.height} Cm")
            }
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                pokemon.types.forEach { tag ->
                    TagItem(tag = tag)
                }
            }
        }
    }
}


@Composable
private fun PropertyItem(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = content,
            fontWeight = FontWeight.W300,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun TagItem(
    tag: String
) {
    Box(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = tag,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
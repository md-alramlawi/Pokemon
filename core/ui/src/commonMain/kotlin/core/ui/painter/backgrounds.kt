package core.ui.painter

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import pokemoncm.core.ui.generated.resources.Res
import pokemoncm.core.ui.generated.resources.bug
import pokemoncm.core.ui.generated.resources.dark
import pokemoncm.core.ui.generated.resources.dragon
import pokemoncm.core.ui.generated.resources.electric
import pokemoncm.core.ui.generated.resources.fairy
import pokemoncm.core.ui.generated.resources.fighting
import pokemoncm.core.ui.generated.resources.fire
import pokemoncm.core.ui.generated.resources.flying
import pokemoncm.core.ui.generated.resources.ghost
import pokemoncm.core.ui.generated.resources.grass
import pokemoncm.core.ui.generated.resources.ground
import pokemoncm.core.ui.generated.resources.ice
import pokemoncm.core.ui.generated.resources.normal
import pokemoncm.core.ui.generated.resources.poison
import pokemoncm.core.ui.generated.resources.psychic
import pokemoncm.core.ui.generated.resources.rock
import pokemoncm.core.ui.generated.resources.steel
import pokemoncm.core.ui.generated.resources.water

@Composable
private fun bugPainter(): Painter = painterResource(Res.drawable.bug)

@Composable
private fun darkPainter(): Painter = painterResource(Res.drawable.dark)

@Composable
private fun dragonPainter(): Painter = painterResource(Res.drawable.dragon)

@Composable
private fun electricPainter(): Painter = painterResource(Res.drawable.electric)

@Composable
private fun fairyPainter(): Painter = painterResource(Res.drawable.fairy)

@Composable
private fun fightingPainter(): Painter = painterResource(Res.drawable.fighting)

@Composable
private fun firePainter(): Painter = painterResource(Res.drawable.fire)

@Composable
private fun flyingPainter(): Painter = painterResource(Res.drawable.flying)

@Composable
private fun ghostPainter(): Painter = painterResource(Res.drawable.ghost)

@Composable
private fun grassPainter(): Painter = painterResource(Res.drawable.grass)

@Composable
private fun groundPainter(): Painter = painterResource(Res.drawable.ground)

@Composable
private fun icePainter(): Painter = painterResource(Res.drawable.ice)

@Composable
private fun normalPainter(): Painter = painterResource(Res.drawable.normal)

@Composable
private fun poisonPainter(): Painter = painterResource(Res.drawable.poison)

@Composable
private fun psychicPainter(): Painter = painterResource(Res.drawable.psychic)

@Composable
private fun rockPainter(): Painter = painterResource(Res.drawable.rock)

@Composable
private fun steelPainter(): Painter = painterResource(Res.drawable.steel)

@Composable
private fun waterPainter(): Painter = painterResource(Res.drawable.water)

@Composable
fun BackgroundsPainterMap(): HashMap<String, Painter> = hashMapOf(
    "bug" to bugPainter(),
    "dark" to darkPainter(),
    "dragon" to dragonPainter(),
    "electric" to electricPainter(),
    "fairy" to fairyPainter(),
    "fighting" to fightingPainter(),
    "fire" to firePainter(),
    "flying" to flyingPainter(),
    "ghost" to ghostPainter(),
    "grass" to grassPainter(),
    "ground" to groundPainter(),
    "ice" to icePainter(),
    "normal" to normalPainter(),
    "poison" to poisonPainter(),
    "psychic" to psychicPainter(),
    "rock" to rockPainter(),
    "steel" to steelPainter(),
    "water" to waterPainter()
)
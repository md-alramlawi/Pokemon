package ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val BottomOnlyRoundedShape = RoundedCornerShape(30.dp).copy(
    topEnd = CornerSize(0.dp),
    topStart = CornerSize(0.dp)
)

val MediumRoundedCornerShape =  RoundedCornerShape(12.dp)
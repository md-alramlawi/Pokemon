package pokemon.feature.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.Pokemon


@Composable
fun CompactInitialStatuses(
    modifier: Modifier = Modifier,
    stats: List<Pokemon.Stat>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Initial Statuses",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        stats.forEachIndexed { index, stat ->
            StatusBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(20.dp),
                label = stat.name,
                value = stat.baseStat,
                percentage = stat.percentage,
                color = statColors[index],
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun ExpandedInitialStatuses(
    modifier: Modifier,
    stats: List<Pokemon.Stat>
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Initial Statuses",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        stats.forEachIndexed { index, stat ->
            StatusBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp),
                label = stat.name,
                value = stat.baseStat,
                percentage = stat.percentage,
                color = statColors[index],
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}
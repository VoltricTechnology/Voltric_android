@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.ui.pages

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

/* ----------------------------------------------------------
 * Entry
 * ---------------------------------------------------------- */

@Composable
fun InsightsScreen() {
    val accent = MaterialTheme.colorScheme.primary   // replace with your `orange` if you have one
    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("Recent", "Summary", "Activity")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Insights",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* share action */ }) {
                Icon(Icons.Default.Share, contentDescription = "Share")
            }
        }

        // Gauge
        ScoreGauge(score = 550, outOf = 1000, accent = accent, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .height(260.dp))

        // Tabs as pills
        PillTabs(
            tabs = tabs,
            selectedIndex = tab,
            onSelect = { tab = it }
        )

        // Cards (static sample data; wire to your VM as needed)
        DistanceCard(accent)
        SavingsCard(accent)
        BatteryHealthCard(accent)
        TripsCard(accent)
        TimeCard()
        CarbonCard(accent)
        JourneysCard(accent)

        Spacer(Modifier.height(20.dp))
    }
}

/* ----------------------------------------------------------
 * Gauge
 * ---------------------------------------------------------- */

@Composable
private fun ScoreGauge(score: Int, outOf: Int, accent: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = modifier
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Canvas(Modifier.fillMaxSize().padding(24.dp)) {
                val sweepMax = 300f   // semi-donut sweep
                val startAngle = 120f // left bottom
                val thickness = 26.dp.toPx()

                val sizePx = min(size.width, size.height)
                val arcDiameter = sizePx * 0.9f
                val topLeft = Offset((size.width - arcDiameter) / 2f, (size.height - arcDiameter) / 2f)
                val arcRect = Rect(topLeft, Size(arcDiameter, arcDiameter))

                // background arc
                drawArc(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    startAngle = startAngle,
                    sweepAngle = sweepMax,
                    useCenter = false,
                    style = Stroke(width = thickness, cap = StrokeCap.Round),
                    size = arcRect.size,
                    topLeft = arcRect.topLeft
                )

                // progress arc
                val pct = (score.coerceAtLeast(0).coerceLessThan(outOf).toFloat() / outOf.toFloat())
                drawArc(
                    color = accent,
                    startAngle = startAngle,
                    sweepAngle = sweepMax * pct,
                    useCenter = false,
                    style = Stroke(width = thickness, cap = StrokeCap.Round),
                    size = arcRect.size,
                    topLeft = arcRect.topLeft
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Driving Score", color = MaterialTheme.colorScheme.onSurface.copy(alpha = .65f))
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                )
                Text("Good", color = MaterialTheme.colorScheme.onSurface.copy(alpha = .75f))
            }
        }
    }
}

private fun Int.coerceLessThan(max: Int) = if (this > max) max else this

/* ----------------------------------------------------------
 * Cards
 * ---------------------------------------------------------- */

@Composable
private fun CardContainer(
    title: String,
    accent: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = modifier
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp)) {
            // Section title with orange subheading style
            Text(
                text = title,
                color = accent,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

/* Distance (weekly bars) */
@Composable
private fun DistanceCard(accent: Color) {
    val days = listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
    val data = listOf(60f, 124f, 70f, 82f, 55f, 40f, 95f) // Miles
    CardContainer(title = "Distance", accent = accent) {
        Text(
            text = "You're driving more this week than usual.",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(text = "${data.sum().toInt()} Miles this Week",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        Spacer(Modifier.height(12.dp))
        SimpleBarChart(
            values = data,
            labels = days,
            accentIndex = 1, // Tue highlighted
            accent = accent,
            height = 120.dp
        )
    }
}

/* Savings (two horizontal bars + amounts) */
@Composable
private fun SavingsCard(accent: Color) {
    CardContainer(title = "Savings", accent = accent) {
        Text(
            text = "In the past month, you've saved 75% more money by driving electric",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(14.dp))

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("£30", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            Tag(text = "You", color = accent)
        }
        HorizontalBar(progress = 0.25f, color = accent)
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("£175", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha=.8f))
            Spacer(Modifier.width(8.dp))
            Tag(text = "Combustion Engine", color = MaterialTheme.colorScheme.surfaceVariant, textColor = MaterialTheme.colorScheme.onSurface)
        }
        HorizontalBar(progress = 0.85f, color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

/* Battery health (percent bars) */
@Composable
private fun BatteryHealthCard(accent: Color) {
    CardContainer(title = "Battery Health", accent = accent) {
        Text(
            text = "Try avoid discharging the battery below 25% too often.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(12.dp))

        val percentages = listOf(55, 18, 80, 32, 60) // arbitrary samples
        PercentBarChart(percentages, accent, height = 120.dp)
    }
}

/* Trips (pie chart + legend) */
@Composable
private fun TripsCard(accent: Color) {
    CardContainer(title = "Trips", accent = accent) {
        Text(
            text = "Majority of your trips in the past month have been under 10 miles",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            PieChart(
                slices = listOf(
                    accent to 0.63f,
                    MaterialTheme.colorScheme.tertiary to 0.17f,
                    MaterialTheme.colorScheme.surfaceVariant to 0.20f
                ),
                size = 140.dp
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Legend(color = accent, label = "63%  < 10 Miles")
                Legend(color = MaterialTheme.colorScheme.tertiary, label = "17%  10 – 20 Miles")
                Legend(color = MaterialTheme.colorScheme.surfaceVariant, label = "20%  20+ Miles")
            }
        }
    }
}

/* Time (large stat) */
@Composable
private fun TimeCard() {
    CardContainer(title = "Time", accent = MaterialTheme.colorScheme.primary) {
        Text(
            text = "You've spent more time driving this week than usual.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "4h 30m",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

/* Carbon (two bars + labels) */
@Composable
private fun CarbonCard(accent: Color) {
    CardContainer(title = "Carbon", accent = accent) {
        Text("Your carbon footprint is 75% smaller by driving electric.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(14.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("164", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(6.dp))
            Text("CO₂g", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Tag("You", accent)
        HorizontalBar(0.18f, accent)
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("940", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha=.85f))
            Spacer(Modifier.width(6.dp))
            Text("CO₂g", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Tag("Combustion Engine", MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurface)
        HorizontalBar(0.95f, MaterialTheme.colorScheme.surfaceVariant)
    }
}

/* Journeys (map preview placeholder with route path) */
@Composable
private fun JourneysCard(accent: Color) {
    CardContainer(title = "Journeys", accent = accent) {
        Text(
            text = "Your work commute time has decreased from 34 minutes to 28 minutes.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(12.dp))
        Surface(
            shape = RoundedCornerShape(14.dp),
            tonalElevation = 0.dp,
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .6f),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // Simple “map” placeholder
            Canvas(Modifier.fillMaxSize().padding(8.dp)) {
                // roads
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(12f, 12f),
                    size = size
                )
                // route polyline
                val p = Path().apply {
                    moveTo(size.width * 0.15f, size.height * 0.75f)
                    cubicTo(
                        size.width * 0.35f, size.height * 0.60f,
                        size.width * 0.55f, size.height * 0.40f,
                        size.width * 0.80f, size.height * 0.30f
                    )
                }
                drawPath(p, color = accent, style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round))
                // start & end pins
                drawCircle(color = Color(0xFF2E7D32), radius = 10.dp.toPx(), center = Offset(size.width * 0.15f, size.height * 0.75f))
                drawCircle(color = Color(0xFFD32F2F), radius = 10.dp.toPx(), center = Offset(size.width * 0.80f, size.height * 0.30f))
            }
        }
    }
}

/* ----------------------------------------------------------
 * Small building blocks
 * ---------------------------------------------------------- */

@Composable
private fun PillTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        tabs.forEachIndexed { i, title ->
            val selected = i == selectedIndex
            Surface(
                shape = CircleShape,
                color = if (selected) Color.White else MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = if (selected) 2.dp else 0.dp,
                shadowElevation = if (selected) 2.dp else 0.dp,
                onClick = { onSelect(i) }
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                    color = if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = .7f),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun Tag(text: String, color: Color, textColor: Color = Color.White) {
    Surface(
        shape = RoundedCornerShape(50),
        color = color,
        contentColor = textColor
    ) {
        Text(text = text, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), fontSize = 12.sp)
    }
}

@Composable
private fun HorizontalBar(progress: Float, color: Color, height: Dp = 10.dp) {
    val pct = progress.coerceIn(0f, 1f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(pct)
                .fillMaxHeight()
                .background(color)
        )
    }
}

@Composable
private fun SimpleBarChart(
    values: List<Float>,
    labels: List<String>,
    accentIndex: Int,
    accent: Color,
    height: Dp
) {
    val max = (values.maxOrNull() ?: 1f).coerceAtLeast(1f)
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(height),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            values.forEachIndexed { index, value ->
                val hPct = (value / max).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(hPct)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (index == accentIndex) accent else MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            labels.forEach { label ->
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun PercentBarChart(values: List<Int>, accent: Color, height: Dp) {
    val max = 100f
    Row(
        Modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEachIndexed { i, v ->
            val h = (v / max).coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(h)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (i % 2 == 1) accent else MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }
}

@Composable
private fun PieChart(slices: List<Pair<Color, Float>>, size: Dp) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .35f),
        modifier = Modifier.size(size)
    ) {
        Canvas(Modifier.fillMaxSize()) {
            var start = -90f
            slices.forEach { (color, pct) ->
                val sweep = 360f * pct
                drawArc(
                    color = color,
                    startAngle = start,
                    sweepAngle = sweep,
                    useCenter = true
                )
                start += sweep
            }
        }
    }
}

@Composable
private fun Legend(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Box(Modifier.size(12.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(8.dp))
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

/* ---------------------------------------------------------- */

@Preview(showBackground = true, backgroundColor = 0xFFF4F4F4)
@Composable
private fun InsightsPreview() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        InsightsScreen()
    }
}

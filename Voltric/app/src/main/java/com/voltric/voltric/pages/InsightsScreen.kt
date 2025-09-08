@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.ui.pages

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.voltric.voltric.ui.theme.orange
import kotlin.math.min

/* ============================ ENTRY ============================ */

@Composable
fun InsightsScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val accent = MaterialTheme.colorScheme.primary
    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("Recent", "Summary", "Activity")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Insights",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* TODO share */ }) {
                Icon(Icons.Default.Share, contentDescription = "Share")
            }
        }

        ScoreGauge(
            score = 550,
            outOf = 1000,
            accent = orange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp)
                .height(260.dp)
        )

        PillTab(
            tabs = tabs,
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it },
            modifier = Modifier.padding(top = 6.dp)
        )

        when (selectedTab) {
            0 -> RecentTab()     // <-- you already have this one
            1 -> SummaryTab(orange)    // <-- we just built this
            2 -> ActivityTab(orange)   // <-- you'll implement later
        }



        Spacer(Modifier.height(20.dp))
    }
}




/* ============================ GAUGE ============================ */

@Composable
private fun RecentTab(){
    DistanceCard(orange)
    SavingsCard(orange)
    BatteryHealthCard(orange)
    TripsCard(orange)
    TimeCard()
    CarbonCard(orange)
    JourneysCard(orange)
}



@Composable
private fun ScoreGauge(
    score: Int,
    outOf: Int,
    accent: Color,
    modifier: Modifier = Modifier
) {
    // HOIST THEME COLORS HERE (outside Canvas)
    val surface = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurface

    Surface(
        shape = RoundedCornerShape(22.dp),
        color = surface,
        tonalElevation = 1.dp,
        modifier = modifier
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Canvas(Modifier.fillMaxSize().padding(24.dp)) {
                val sweepMax = 300f
                val startAngle = 120f
                val thickness = 26.dp.toPx()

                val sizePx = min(size.width, size.height)
                val arcDiameter = sizePx * 0.9f
                val topLeft = Offset((size.width - arcDiameter) / 2f, (size.height - arcDiameter) / 2f)
                val arcRect = Rect(topLeft, Size(arcDiameter, arcDiameter))

                // background arc – uses captured color
                drawArc(
                    color = surfaceVariant,
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
                    color = orange,
                    startAngle = startAngle,
                    sweepAngle = sweepMax * pct,
                    useCenter = false,
                    style = Stroke(width = thickness, cap = StrokeCap.Round),
                    size = arcRect.size,
                    topLeft = arcRect.topLeft
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Driving Score", color = onSurface.copy(alpha = .65f))
                Text(text = "$score", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold))
                Text("Good", color = onSurface.copy(alpha = .75f))
            }
        }
    }
}

private fun Int.coerceLessThan(max: Int) = if (this > max) max else this

/* ============================ CARDS ============================ */

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
            Text(
                text = title,
                color = orange,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun DistanceCard(accent: Color) {
    val days = listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
    val data = listOf(60f, 124f, 70f, 82f, 55f, 40f, 95f)
    CardContainer(title = "Distance", accent = orange) {
        Text("You're driving more this week than usual.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "${data.sum().toInt()} Miles this Week",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(12.dp))
        SimpleBarChart(
            values = data,
            labels = days,
            accentIndex = 1,
            accent = orange,
            height = 120.dp
        )
    }
}

@Composable
private fun SavingsCard(accent: Color) {
    CardContainer(title = "Savings", accent = orange) {
        Text("In the past month, you've saved 75% more money by driving electric", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(14.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("£30", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            Tag("You", accent)
        }
        HorizontalBar(0.25f, accent)
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("£175", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha=.85f))
            Spacer(Modifier.width(8.dp))
            Tag("Combustion Engine", MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurface)
        }
        HorizontalBar(0.95f, MaterialTheme.colorScheme.surfaceVariant)
    }
}

@Composable
private fun BatteryHealthCard(accent: Color) {
    CardContainer(title = "Battery Health", accent = orange) {
        Text("Try avoid discharging the battery below 25% too often.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))
        val percentages = listOf(55, 18, 80, 32, 60)
        PercentBarChart(percentages, accent, height = 120.dp)
    }
}

@Composable
private fun TripsCard(accent: Color) {
    CardContainer(title = "Trips", accent = orange) {
        Text("Majority of your trips in the past month have been under 10 miles", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            PieChart(
                slices = listOf(
                    orange to 0.63f,
                    MaterialTheme.colorScheme.tertiary to 0.17f,
                    MaterialTheme.colorScheme.surfaceVariant to 0.20f
                ),
                size = 140.dp
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Legend(orange, "63%  < 10 Miles")
                Legend(MaterialTheme.colorScheme.tertiary, "17%  10 – 20 Miles")
                Legend(MaterialTheme.colorScheme.surfaceVariant, "20%  20+ Miles")
            }
        }
    }
}

@Composable
private fun TimeCard() {
    CardContainer(title = "Time", accent = MaterialTheme.colorScheme.primary) {
        Text("You've spent more time driving this week than usual.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))
        Text("4h 30m", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold))
    }
}

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
        Tag("You", orange)
        HorizontalBar(0.18f, orange)
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

@Composable
private fun JourneysCard(accent: Color) {
    CardContainer(title = "Journeys", accent = orange) {
        Text("Your work commute time has decreased from 34 minutes to 28 minutes.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

        Surface(
            shape = RoundedCornerShape(14.dp),
            tonalElevation = 0.dp,
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .6f),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Canvas(Modifier.fillMaxSize().padding(8.dp)) {
                // map background
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(12f, 12f),
                    size = size
                )
                // route path
                val p = Path().apply {
                    moveTo(size.width * 0.15f, size.height * 0.75f)
                    cubicTo(
                        size.width * 0.35f, size.height * 0.60f,
                        size.width * 0.55f, size.height * 0.40f,
                        size.width * 0.80f, size.height * 0.30f
                    )
                }
                drawPath(p, color = orange, style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round))
                // markers
                drawCircle(color = Color(0xFF2E7D32), radius = 10.dp.toPx(), center = Offset(size.width * 0.15f, size.height * 0.75f))
                drawCircle(color = Color(0xFFD32F2F), radius = 10.dp.toPx(), center = Offset(size.width * 0.80f, size.height * 0.30f))
            }
        }
    }
}

/* ====================== SMALL BUILDING BLOCKS ====================== */

@Composable
private fun PillTab(
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
    Surface(shape = RoundedCornerShape(50), color = color, contentColor = textColor) {
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
                        .background(if (index == accentIndex) orange else MaterialTheme.colorScheme.surfaceVariant)
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
                    .background(if (i % 2 == 1) orange else MaterialTheme.colorScheme.surfaceVariant)
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
                drawArc(color = color, startAngle = start, sweepAngle = sweep, useCenter = true)
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



/* ========================= SUMMARY TAB ========================= */

@Composable
fun SummaryTab(accent: Color) {
    // You can wire real data from your VM here
    TotalSavedCard(accent)
    TotalEnergyUsedCard(accent, you = 340, average = 302, deltaText = "You're using 8% more energy than our average Voltric customer.")
    CarbonSavedCardSummary(accent, value = 740)
    TotalTripsDoneCard(accent, totalTrips = 204, avgText = "You make on average 22 trips a month.")
    TotalDistanceCardSummary(accent, miles = 8302, percentileText = "You're driving more than 78% of UK drivers.")
}

/* -------------------- Cards for Summary -------------------- */

@Composable
private fun TotalSavedCard(accent: Color) {
    // Orange, prominent card with info icon + inner white panel
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = accent,
        contentColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Total Saved",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White.copy(alpha = .92f)
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = "When compared to typical petrol combustion engine driving the same distance.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = .9f)
            )

            Spacer(Modifier.height(14.dp))
            Text("£1,137", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold))

            Spacer(Modifier.height(14.dp))

            // inner white panel
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        text = "You're spending 54% less on vehicle ownership than the average leaser.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "£3,475",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Yearly Costs",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    // compare to average bar
                    HorizontalBar(progress = 0.65f, color = MaterialTheme.colorScheme.surfaceVariant, height = 12.dp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Average lease for similar car",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun TotalEnergyUsedCard(
    accent: Color,
    you: Int,
    average: Int,
    deltaText: String
) {
    CardContainer(title = "Total Energy Used", accent = accent) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "340",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = "kilowatts",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(8.dp))
        Divider()
        Spacer(Modifier.height(8.dp))

        Text(deltaText, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

        // You
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$you",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.width(6.dp))
            Text("kWh", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Tag("You", accent)
        HorizontalBar(progress = 0.80f, color = accent)
        Spacer(Modifier.height(10.dp))

        // Average
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$average",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .85f)
            )
            Spacer(Modifier.width(6.dp))
            Text("kWh", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Tag("Average Voltric Customer", MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurface)
        HorizontalBar(progress = 0.70f, color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

@Composable
private fun CarbonSavedCardSummary(accent: Color, value: Int) {
    CardContainer(title = "Carbon Saved", accent = accent) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$value",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.width(6.dp))
            Text("CO₂g", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(8.dp))
        Divider()
        Spacer(Modifier.height(8.dp))
        Text("From Driving Electric", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun TotalTripsDoneCard(accent: Color, totalTrips: Int, avgText: String) {
    CardContainer(title = "Total Trips Done", accent = accent) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$totalTrips",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.width(6.dp))
            Text("Trips", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(8.dp))
        Divider()
        Spacer(Modifier.height(8.dp))

        Text(avgText, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

        // Months header
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Nov","Dec","Jan","Feb","Mar","Apr").forEach {
                Text(text = it, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(8.dp))

        // Simple 6x7 heatmap (columns = months)
        HeatmapCalendar(
            cols = 6,
            rows = 7,
            value = { col, row ->
                // fake pattern; wire with real data if you have it
                val seed = listOf(0.8f, 0.2f, 0.6f, 0.4f, 0.7f, 0.3f)[col]
                (seed - row * 0.07f).coerceIn(0f, 1f)
            },
            cellSize = 18.dp,
            gap = 6.dp,
            lowColor = MaterialTheme.colorScheme.surfaceVariant,
            highColor = accent
        )

        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Text("Less", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            Spacer(Modifier.width(6.dp))
            Box(Modifier.size(12.dp).clip(RoundedCornerShape(3.dp)).background(MaterialTheme.colorScheme.surfaceVariant))
            Spacer(Modifier.width(6.dp))
            Box(Modifier.size(12.dp).clip(RoundedCornerShape(3.dp)).background(accent.copy(alpha = .6f)))
            Spacer(Modifier.width(6.dp))
            Box(Modifier.size(12.dp).clip(RoundedCornerShape(3.dp)).background(accent))
            Spacer(Modifier.width(6.dp))
            Text("More", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
        }
    }
}

@Composable
private fun TotalDistanceCardSummary(accent: Color, miles: Int, percentileText: String) {
    CardContainer(title = "Total Distance", accent = accent) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "%,d".format(miles),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.width(6.dp))
            Text("Miles", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(8.dp))
        Divider()
        Spacer(Modifier.height(8.dp))
        Text(percentileText, style = MaterialTheme.typography.bodyMedium)
    }
}

/* -------------------- Heatmap helper -------------------- */

@Composable
private fun HeatmapCalendar(
    cols: Int,
    rows: Int,
    value: (col: Int, row: Int) -> Float, // 0f..1f
    cellSize: Dp,
    gap: Dp,
    lowColor: Color,
    highColor: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(gap)) {
        repeat(rows) { r ->
            Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                repeat(cols) { c ->
                    val v = value(c, r).coerceIn(0f, 1f)
                    val cellColor = Color(
                        red = lerp(lowColor.red, highColor.red, v),
                        green = lerp(lowColor.green, highColor.green, v),
                        blue = lerp(lowColor.blue, highColor.blue, v),
                        alpha = 1f
                    )
                    Box(
                        modifier = Modifier
                            .size(cellSize)
                            .clip(RoundedCornerShape(4.dp))
                            .background(cellColor)
                    )
                }
            }
        }
    }
}

private fun lerp(a: Float, b: Float, t: Float) = a + (b - a) * t


/* ============================ Activity TAb ============================ */
    // You can wire real data from your VM here


/* ========================= ACTIVITY TAB ========================= */

private data class Trip(
    val id: String,
    val title: String,          // e.g., "Yesterday Evening"
    val from: String,           // "Clifton"
    val to: String,             // "Bath"
    val miles: Float,
    val avgSpeedMph: Int,
    val time: String,           // "20:15"
    val energyKwh: Int
)

@Composable
fun ActivityTab(accent: Color) {
    // sample data – replace with your ViewModel state
    val trips = listOf(
        Trip("1","Yesterday Evening","Clifton","Bath",12.11f,28,"20:15",14),
        Trip("2","Yesterday Morning","Clifton","Bristol",9.42f,25,"18:02",10),
        Trip("3","Tue Afternoon","Bath","Clifton",14.30f,31,"26:40",16),
    )

    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )

        trips.forEach { trip ->
            TripCard(trip = trip, accent = accent, onClick = { /* open details */ })
        }

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun TripCard(trip: Trip, accent: Color, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // tiny map thumbnail
                MapThumb(accent = accent, modifier = Modifier.size(48.dp))

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = trip.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = "${trip.from} → ${trip.to}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(Modifier.height(12.dp))
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = .15f))
            Spacer(Modifier.height(10.dp))

            // four stats in a row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TripStat(
                    value = String.format("%.2f", trip.miles),
                    unit = "Miles"
                )
                TripStat(
                    value = "${trip.avgSpeedMph}",
                    unit = "Avg. Speed",
                    unitSuffix = "mph"
                )
                TripStat(
                    value = trip.time,
                    unit = "Time"
                )
                TripStat(
                    value = "${trip.energyKwh}",
                    unit = "Energy Used",
                    unitSuffix = "kWh"
                )
            }
        }
    }
}

@Composable
private fun TripStat(value: String, unit: String, unitSuffix: String? = null) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            if (!unitSuffix.isNullOrBlank()) {
                Spacer(Modifier.width(2.dp))
                Text(
                    text = unitSuffix,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Text(
            text = unit,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

/* small “map” thumbnail to match the mock */
@Composable
private fun MapThumb(accent: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Canvas(Modifier.fillMaxSize().padding(4.dp)) {
            // background “map”
            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(8f, 8f),
                size = size
            )
            // route
            val p = Path().apply {
                moveTo(size.width * 0.20f, size.height * 0.75f)
                cubicTo(
                    size.width * 0.40f, size.height * 0.55f,
                    size.width * 0.55f, size.height * 0.40f,
                    size.width * 0.80f, size.height * 0.30f
                )
            }
            drawPath(p, color = accent, style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round))
            // pins
            drawCircle(color = Color(0xFF2E7D32), radius = 6.dp.toPx(), center = Offset(size.width * 0.20f, size.height * 0.75f))
            drawCircle(color = Color(0xFFD32F2F), radius = 6.dp.toPx(), center = Offset(size.width * 0.80f, size.height * 0.30f))
        }
    }
}





/* ============================ PREVIEW ============================ */

@Preview(showBackground = true, backgroundColor = 0xFFF4F4F4)
@Composable
private fun InsightsPreview() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        InsightsScreen()
    }
}

@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.ui.pages

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.R
import com.voltric.voltric.ui.theme.VoltricTheme
import com.voltric.voltric.ui.theme.orange

@Composable
fun CarAppMainScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Status", "Climate", "Battery", "Safety")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopWelcomeSection()
        CarImageSection()

        Spacer(modifier = Modifier.height(25.dp))
        PillTabs(
            tabs = tabs,
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it }
        )

        when (selectedTab) {
            0 -> StatusTab()
            1 -> ClimateTab()
            2 -> BatteryTab()
            3 -> SafetyTab()
        }
    }
}

/* ---------- Pill Tabs ---------- */
@Composable
fun PillTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(tabs) { index, title ->
            val selected = index == selectedIndex

            Surface(
                shape = CircleShape,
                color = if (selected) Color.White else MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = if (selected) 2.dp else 0.dp,
                shadowElevation = if (selected) 2.dp else 0.dp,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onSelect(index) }
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    color = if (selected) orange
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/* -------------------- Header + Image -------------------- */
@Composable
fun TopWelcomeSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome Phil",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Your vehicle is ready",
            color = MaterialTheme.colorScheme.outline,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CarImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Car image
        Image(
            painter = painterResource(id = R.drawable.kia_ev),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 160.dp, max = 220.dp)

                .clip(RoundedCornerShape(0.dp))
                .offset(x = -25.dp)
        )


    }
}

/* -------------------- Tabs content -------------------- */
@Composable
fun StatusTab() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(Modifier.weight(1f)) {
            BatteryIndicator(
                batteryLevel = 85,
                range = "212 km",
                lastCharge = "Last charge 2w ago"
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(Modifier.weight(1f)) { ClimateControl() }
    }
}

@Composable
fun ClimateTab() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Climate Control",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "20°C",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Cooling On",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(24.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    ClimateControlButton(Icons.Default.Add) {}
                    ClimateControlButton(Icons.Default.Close) {}
                    ClimateControlButton(Icons.Default.Face) {}
                }
            }
        }
    }
}

@Composable
fun BatteryTab() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Battery Status",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(24.dp))
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
                    CircularProgressIndicator(
                        progress = 0.85f,
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 12.dp,
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = "85%",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Range: 212 km",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text(text = "Start Charging", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SafetyTab() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Safety Features",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
                SafetyFeatureItem("Alarm System", "Active", Icons.Default.Settings)
                SafetyFeatureItem("Location Tracking", "On", Icons.Default.LocationOn)
                SafetyFeatureItem("Emergency Brake", "Ready", Icons.Default.Build)
            }
        }
    }
}

/* -------------------- Reusables -------------------- */
@Composable
fun SafetyFeatureItem(title: String, status: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = status in listOf("Active", "On", "Ready"),
            onCheckedChange = {},
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun ClimateControlButton(icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.size(50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = CircleShape
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
        ) {
            Icon(
                icon, null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/* -------- Battery & Climate cards used in Status tab -------- */
@Composable
fun BatteryIndicator(
    batteryLevel: Int,
    range: String,
    lastCharge: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = "Battery", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = lastCharge, color = Color.Black, fontSize = 12.sp)
        Spacer(Modifier.height(25.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.Center) {
                Canvas(
                    modifier = Modifier
                        .width(50.dp)
                        .height(100.dp)
                ) {
                    val w = size.width
                    val h = size.height
                    val cr = 8.dp.toPx()
                    val tipW = 16.dp.toPx()
                    val tipH = 4.dp.toPx()

                    drawRoundRect(
                        color = Color.Black,
                        size = androidx.compose.ui.geometry.Size(w, h - tipH),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(cr),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                    )

                    drawRoundRect(
                        color = Color.Black,
                        topLeft = androidx.compose.ui.geometry.Offset((w - tipW) / 2, -10f),
                        size = androidx.compose.ui.geometry.Size(tipW, tipH),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(cr / 2)
                    )

                    val fillH = (h - tipH - 4.dp.toPx()) * (batteryLevel / 100f)
                    val colors = when {
                        batteryLevel > 60 -> listOf(Color(0xFF4CAF50), Color(0xFF8BC34A))
                        batteryLevel > 30 -> listOf(Color(0xFFFFC107), Color(0xFFFFEB3B))
                        else -> listOf(Color(0xFFF44336), Color(0xFFFF5252))
                    }

                    if (batteryLevel > 0) {
                        drawRoundRect(
                            brush = linearGradient(
                                colors = colors,
                                start = androidx.compose.ui.geometry.Offset(0f, h - tipH - 2.dp.toPx()),
                                end = androidx.compose.ui.geometry.Offset(0f, h - tipH - 2.dp.toPx() - fillH)
                            ),
                            size = androidx.compose.ui.geometry.Size(w - 4.dp.toPx(), fillH),
                            topLeft = androidx.compose.ui.geometry.Offset(2.dp.toPx(), h - tipH - 2.dp.toPx() - fillH),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cr - 1.dp.toPx())
                        )
                    }
                }
            }

            Spacer(Modifier.width(10.dp))

            Column {
                Text(text = range, color = orange, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(20.dp))
                Text(text = "$batteryLevel%", color = orange, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ClimateControl(modifier: Modifier = Modifier) {
    var targetTemp by remember { mutableStateOf(20) }
    var isCoolingOn by remember { mutableStateOf(true) }
    var isAutoMode by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = "Climate", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "Interior 27°", color = Color.Black, fontSize = 12.sp)
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { if (targetTemp > 16) targetTemp-- },
                contentAlignment = Alignment.Center
            ) { Text(text = "-", color = orange, fontSize = 22.sp) }

            Spacer(Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "+${targetTemp}°", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "Target", color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { if (targetTemp < 30) targetTemp++ },
                contentAlignment = Alignment.Center
            ) { Text(text = "+", color = orange, fontSize = 20.sp) }
        }

        Spacer(Modifier.height(15.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { isCoolingOn = !isCoolingOn }) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(if (isCoolingOn) Color(0xFF2196F3) else Color.LightGray),
                    contentAlignment = Alignment.Center
                ) { Text(text = if (isCoolingOn) "❄️" else "🌡️", fontSize = 20.sp) }
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Cooling",
                    color = if (isCoolingOn) Color(0xFF2196F3) else Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = if (isCoolingOn) FontWeight.Bold else FontWeight.Normal
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { isAutoMode = !isAutoMode }) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isAutoMode) Color(0xFFFFFFF) else Color.LightGray),
                    contentAlignment = Alignment.Center
                ) { Text(text = "A", color = orange, fontSize = 24.sp, fontWeight = FontWeight.Bold) }
            }
        }
    }
}

/* -------------------- Preview -------------------- */
@Preview(showBackground = true)
@Composable
fun CarAppMainScreenPreview() {
    VoltricTheme { CarAppMainScreen() }
}

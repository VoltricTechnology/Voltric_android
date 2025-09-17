
@file:OptIn(ExperimentalMaterial3Api::class)
package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.EvStation
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.R
import com.voltric.voltric.ui.theme.VoltricTheme
import com.voltric.voltric.ui.theme.orange

data class ChargingActivity(
    val id: String,
    val logo: Painter,
    val title: String,        // e.g. "35% Charge"
    val subtitle: String,     // e.g. "£16.29 • 12:35 Yesterday"
)

data class Charger(
    val id: String,
    val logo: Painter,
    val name: String,
    val distance: String,     // "60m"
    val type: String,         // "EV Fast Charging Station"
    val compatibilityText: String, // "Compatible" or "Adapter Required"
    val isCompatible: Boolean
)

@Composable
fun ChargingScreen(modifier: Modifier = Modifier) {
    // sample data - replace painterResource ids with your own drawables
    // sample data - now safe because we’re inside a Composable
    val activities = listOf(
        ChargingActivity("a1", painterResource(R.drawable.ic_bp), "35% Charge", "£16.29 • 12:35 Yesterday"),
        ChargingActivity("a2", painterResource(R.drawable.ic_bp), "89% Charge", "£36.29 • 14:31 16th May"),
        ChargingActivity("a3", painterResource(R.drawable.ic_shell), "9% Charge", "£3.29 • 14:31 1st May")
    )

    val chargers = listOf(
        Charger(
            "c1",
            painterResource(R.drawable.ic_bp),
            "BP Pulse",
            "60m",
            "EV Fast Charging Station",
            "Compatible",
            true
        ),
        Charger(
            "c2",
            painterResource(R.drawable.ic_tesla),
            "Tesla Supercharger",
            "60m",
            "EV Fast Charging Station",
            "Adapter Required",
            false
        )
    )

    // page background color similar to mock (soft gray)
    val pageBg = MaterialTheme.colorScheme.background

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(pageBg),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            // header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Charging",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Car Switcher",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // search bar – filled rounded surface to match mock
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EvStation,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Find a Charger",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    // mic placeholder
                    Icon(
                        painter = painterResource(R.drawable.ic_microphone), // replace or remove
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section title
            Text(
                text = "Latest Charging Activity",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(start = 18.dp, bottom = 8.dp)
            )
        }

        // grouped white card containing activities (rounded and elevated)
        item {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                tonalElevation = 1.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.background(color = Color.White)){
                    activities.forEachIndexed { index, act ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* open activity */ }
                                .padding(horizontal = 14.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // circular logo
                            Surface(
                                shape = CircleShape,
                                color = Color.Transparent,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Image(
                                    painter = act.logo,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = act.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = act.subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            }

                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // divider except after last item
                        if (index < activities.lastIndex) {
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(start = 74.dp) // indent to align under text
                            )
                        }
                    }

                    // footer row inside the same card: All Charging Activity -> count (orange)
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f), thickness = 1.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* open all activity */ }
                            .padding(horizontal = 14.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // small square icon background
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))
                        Text("All Charging Activity", modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
                        Text("127", color = orange, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Chargers Near You",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(start = 18.dp, bottom = 8.dp)
            )
        }

        // chargers list inside a rounded card
        item {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                tonalElevation = 1.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.background(color = Color.White)){
                    chargers.forEachIndexed { index, ch ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* open charger */ }
                                .padding(horizontal = 14.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // circular logo
                            Surface(
                                shape = CircleShape,
                                color = Color.Transparent,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Image(painter = ch.logo, contentDescription = null, modifier = Modifier.size(48.dp).clip(CircleShape))
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(ch.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "${ch.distance} • ${ch.type}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                // compatibility tag + plugs count placeholder
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (ch.isCompatible) Color(0xFFE6F4EA) else Color(0xFFFDECEA),
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) {
                                        Text(
                                            text = ch.compatibilityText,
                                            color = if (ch.isCompatible) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            fontSize = 12.sp
                                        )
                                    }

                                    // stub for connectors count - replace with actual data
                                    Text("⚡ 2", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                                }
                            }

                            // small distance pill on the right (10m)
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.size(width = 44.dp, height = 36.dp),
                                tonalElevation = 0.dp
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = "10m", fontSize = 12.sp)
                                }
                            }
                        }

                        if (index < chargers.lastIndex) {
                            Divider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(start = 80.dp)
                            )
                        }
                    }

                    // See More footer
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f), thickness = 1.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* see more */ }
                            .padding(horizontal = 14.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(40.dp)
                        ) {
                            // placeholder icon (plug)
                            Icon(
                                painter = painterResource(R.drawable.ic_plug), // replace drawable
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("See More", modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChargingScreenPreview() {
    VoltricTheme {
        ChargingScreen()
    }
}

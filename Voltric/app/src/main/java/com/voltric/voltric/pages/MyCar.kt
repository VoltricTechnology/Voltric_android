@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mycars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.R
import com.voltric.voltric.ui.theme.VoltricTheme

@Composable
fun MyCarsScreen() {
    val surface = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface

    Surface(color = surface) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            // Title
            Text(
                text = "My Cars",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )

            // Car image
            Image(
                painter = painterResource(id = R.drawable.kia_ev),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 160.dp, max = 220.dp)

                    .clip(RoundedCornerShape(24.dp))
                    .offset(x = -25.dp)
            )

            // Name + status chip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Kia EV6", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold))
                    Spacer(Modifier.height(4.dp))
                    Text("2022 – 75kwh", style = MaterialTheme.typography.bodyMedium, color = onSurface.copy(alpha = 0.7f))
                }
                StatusChip(text = "ACTIVE")
            }

            // KPI cards
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KpiCard(title = "Monthly", value = "£590")
                KpiCard(title = "Remaining", value = "3", subtitle = "Months")
                KpiCard(title = "Date Started", value = "12", subtitle = "Dec")
            }

            // Grabber + sheet
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(bottom = 24.dp)
                ) {
                    // grab handle
                    Box(
                        Modifier
                            .padding(top = 14.dp)
                            .size(width = 56.dp, height = 4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(onSurface.copy(alpha = 0.2f))
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        "Manage",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 8.dp)
                    )

                    CardList(
                        items = listOf(
                            ListRow(Icons.Default.History, "Renewal"),
                            ListRow(Icons.Default.Upgrade, "Explore Upgrades"),
                            ListRow(Icons.Default.AccountCircle, "Add Drivers"),
                            ListRow(Icons.Default.CreditCard, "Payment History")
                        )
                    )

                    Text(
                        "Help",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 8.dp)
                    )

                    CardList(
                        items = listOf(
                            ListRow(Icons.Default.HelpOutline, "Report Issue"),
                            ListRow(Icons.Default.HelpOutline, "Cancel or Pause Subscription")
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatusChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun RowScope.KpiCard(
    title: String,
    value: String,
    subtitle: String? = null,
    corner: Dp = 20.dp
) {
    Surface(
        shape = RoundedCornerShape(corner),
        tonalElevation = 2.dp,
        modifier = Modifier
            .weight(1f)
            .height(88.dp)
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    value,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!subtitle.isNullOrBlank()) {
                    Spacer(Modifier.width(6.dp))
                    Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }
            }
        }
    }
}

data class ListRow(
    val leading: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
)

@Composable
private fun CardList(items: List<ListRow>) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
    ) {
        Column(Modifier.fillMaxWidth()) {
            items.forEachIndexed { index, row ->
                ListItem(
                    headlineContent = { Text(row.title) },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(row.leading, contentDescription = null)
                        }
                    },
                    trailingContent = {
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(16.dp))
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (index < items.lastIndex) Modifier
                                .padding(horizontal = 16.dp)
                                .drawDivider() else Modifier
                        )
                )
            }
        }
    }
}

private fun Modifier.drawDivider(): Modifier =
    this.then(
        Modifier
            .padding(bottom = 8.dp)
            .background(Color.Transparent)
    )

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
private fun MyCarsPreview() {
    VoltricTheme  {
        MyCarsScreen()
    }
}

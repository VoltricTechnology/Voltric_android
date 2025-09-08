@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.ui.theme.VoltricTheme
import com.voltric.voltric.ui.theme.orange

/* ----------------------- DATA ----------------------- */

data class RowItem(
    val icon: ImageVector,
    val title: String,
    val iconTint: Color? = null,   // theme fallback applied in SectionCard
    val iconBg: Color? = null,     // theme fallback applied in SectionCard
    val badgeText: String? = null,
    val badgeTint: Color? = null,  // theme fallback applied in SectionCard
    val onClick: () -> Unit = {}
)

/* ----------------------- SCREEN ----------------------- */

@Composable
fun MoreScreen(
    onAccountClick: () -> Unit = {},
    onPaymentClick: () -> Unit = {},
    onIdentityClick: () -> Unit = {},
    onClaimClick: () -> Unit = {},
    onIncidentClick: () -> Unit = {},
    onContactClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onTermsClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        // Title
        Text(
            text = "More",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 8.dp)
        )

        SectionHeader("My Account")
        SectionCard(
            items = listOf(
                RowItem(
                    icon = Icons.Default.AccountCircle,
                    title = "Account & Personal Details"
                ),
                RowItem(
                    icon = Icons.Default.CreditCard,
                    title = "Payment Methods",
                    badgeText = "1",
                    badgeTint = orange,
                    onClick = onPaymentClick
                ),
                RowItem(
                    icon = Icons.Default.Group,
                    title = "Identity Centre"
                )
            )
        )

        SectionHeader("Help")
        SectionCard(
            items = listOf(
                RowItem(
                    icon = Icons.Default.DirectionsCar,
                    title = "Make a Claim",
                    iconTint = MaterialTheme.colorScheme.primary,
                    iconBg = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    onClick = onClaimClick
                ),
                RowItem(
                    icon = Icons.Default.Report,
                    title = "Report an Incident",
                    iconTint = Color(0xFFD32F2F),
                    iconBg = Color(0xFFFDE7E9),
                    onClick = onIncidentClick
                ),
                RowItem(
                    icon = Icons.Default.Call,
                    title = "Contact Us",
                    onClick = onContactClick
                )
            )
        )

        SectionCard(
            items = listOf(
                RowItem(Icons.Default.Settings, "App Settings", onClick = onSettingsClick),
                RowItem(Icons.Default.PrivacyTip, "Privacy Policy", onClick = onPrivacyClick),
                RowItem(Icons.Default.Description, "Terms & Conditions", onClick = onTermsClick)
            ),
            showTopLabel = true,
            topLabel = "App Settings"
        )

        Spacer(Modifier.height(12.dp))

        // Version footer
        Text(
            text = "v1.01",
            color = MaterialTheme.colorScheme.outline,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )
    }
}

/* ----------------------- BUILDING BLOCKS ----------------------- */

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(start = 24.dp, top = 12.dp, bottom = 8.dp)
    )
}

@Composable
private fun SectionCard(
    items: List<RowItem>,
    showTopLabel: Boolean = false,
    topLabel: String = ""
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.fillMaxWidth()) {
            if (showTopLabel) {
                Text(
                    text = topLabel,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(start = 16.dp, top = 14.dp, bottom = 8.dp)
                )
                Divider(Modifier.padding(horizontal = 16.dp))
            }

            items.forEachIndexed { index, item ->
                // fallbacks to theme if a color wasn't provided in RowItem
                val iconTint = item.iconTint ?: MaterialTheme.colorScheme.onSurfaceVariant
                val iconBg = item.iconBg ?: MaterialTheme.colorScheme.surfaceVariant
                val badgeTint = item.badgeTint ?: MaterialTheme.colorScheme.primary

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 12.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = item.onClick)
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Leading icon in soft rounded square
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(iconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = item.icon, contentDescription = null, tint = iconTint)
                    }

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    if (!item.badgeText.isNullOrBlank()) {
                        Text(
                            text = item.badgeText,
                            color = badgeTint,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                }

                if (index < items.lastIndex) {
                    Divider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 64.dp) // inset to align under text
                    )
                }
            }
        }
    }
}

/* ----------------------- PREVIEW ----------------------- */

@Preview(showBackground = true)
@Composable
private fun MorePreview() {
    VoltricTheme { MoreScreen() }
}

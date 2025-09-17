@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.voltric.voltric.nav.NavRoute
import com.voltric.voltric.ui.theme.VoltricTheme

// Colors tuned to match the iOS-like UI you showed
private val PageBg = Color(0xFFF2F2F7)
private val CardBg = Color.White
private val DividerColor = Color(0xFFE5E5EA)
private val LabelText = Color(0xFF1C1C1E)
private val ValueText = Color(0xFF8E8E93)
private val IconBg = Color(0xFFF2F2F7)
private val IconTint = Color(0xFF6C6C70)

@Composable
fun SubscriptionScreen(navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PageBg),
        contentPadding = PaddingValues(vertical = 18.dp, horizontal = 0.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            // Title
            Text(
                text = "Subscription",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = LabelText,
                modifier = Modifier.padding(start = 18.dp, bottom = 12.dp)
            )
        }

        item {
            // Top info card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(14.dp),
                color = CardBg
            ) {
                Column {
                    InfoRow(label = "Joined", value = "March 2024", showDivider = true)
                    InfoRow(
                        label = "Drivers",
                        value = "2 Drivers",
                        showDivider = true,
                        showChevron = true,
                        onClick = { navController.navigate(NavRoute.ADD_DRIVER) } // open add driver flow
                    )
                    InfoRow(label = "Monthly Mileage", value = "1000 Miles", showDivider = true)
                    InfoRow(label = "Monthly", value = "£750", showDivider = true)
                    InfoRow(label = "Subscription Type", value = "12 Month Period", showDivider = true)
                    InfoRow(label = "Subscription End", value = "March 2025", showDivider = false)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            SectionHeader(text = "Upgrade")
            CardSection {
                ActionRow(
                    icon = Icons.Default.Person,
                    text = "Add New Driver",
                    showDivider = true,
                    onClick = { navController.navigate(NavRoute.ADD_DRIVER) }
                )
                ActionRow(
                    icon = Icons.Default.DirectionsCar,
                    text = "Adjust Mileage",
                    showDivider = false,
                    onClick = { navController.navigate(NavRoute.ADJUST_MILEAGE) }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(18.dp)) }

        item {
            SectionHeader(text = "Manage")
            CardSection {
                ActionRow(
                    icon = Icons.Default.History,
                    text = "Renewal",
                    showDivider = true,
                    onClick = { navController.navigate(NavRoute.RENEWAL) }
                )
                ActionRow(
                    icon = Icons.Default.Close,
                    text = "Cancel Subscription",
                    showDivider = false,
                    onClick = { navController.navigate(NavRoute.CANCEL_SUBSCRIPTION) }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(18.dp)) }

        item {
            SectionHeader(text = "Account")
            CardSection {
                ActionRow(
                    icon = Icons.Default.CreditCard,
                    text = "Billing History",
                    showDivider = true,
                    onClick = { navController.navigate(NavRoute.BILLING_HISTORY) }
                )
                ActionRow(
                    icon = Icons.Default.CreditCard,
                    text = "Payment Method",
                    showDivider = true,
                    onClick = { navController.navigate(NavRoute.PAYMENT_METHOD) }
                )
                ActionRow(
                    icon = Icons.Default.Person,
                    text = "Account & Personal Details",
                    showDivider = false,
                    onClick = { navController.navigate(NavRoute.PERSONAL_DETAILS) }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(36.dp)) }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        color = LabelText,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(start = 18.dp, bottom = 8.dp)
    )
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    showDivider: Boolean,
    showChevron: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, modifier = Modifier.weight(1f), color = LabelText)
            Text(text = value, color = ValueText)
            if (showChevron) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = ValueText,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        if (showDivider) {
            Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(start = 14.dp))
        }
    }
}

@Composable
private fun ActionRow(
    icon: ImageVector,
    text: String,
    showDivider: Boolean,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = IconBg
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = IconTint, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text, modifier = Modifier.weight(1f), color = LabelText)
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null, tint = ValueText, modifier = Modifier.size(16.dp))
        }
        if (showDivider) {
            Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(start = 70.dp))
        }
    }
}

@Composable
private fun CardSection(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(12.dp),
        color = CardBg
    ) {
        Column { content() }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscriptionScreenPreview() {
    VoltricTheme {
        // use a real nav controller in preview to avoid "null" - preview will still compile in IDE
        val navController = rememberNavController()
        SubscriptionScreen(navController = navController)
    }
}

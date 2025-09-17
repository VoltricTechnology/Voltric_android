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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.voltric.voltric.nav.NavRoute
import com.voltric.voltric.ui.theme.VoltricTheme

// Custom iOS-like green and page bg
private val iOSGreen = Color(0xFF34C759)
private val pageBg = Color(0xFFF5F5F7)

/**
 * MoreScreen - uses NavRoute constants so route names cannot drift.
 * Pass the NavController from your NavHost: MoreScreen(navController = navController)
 */
@Composable
fun MoreScreen(
    navController: NavController? = null,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {}
) {
    var notificationsEnabled by remember { mutableStateOf(true) }

    fun handleRowTap(id: String) {
        when (id) {
            // Glovebox
            "welcome" -> navController?.navigate(NavRoute.WELCOME_PACK)
            "manual" -> navController?.navigate(NavRoute.CAR_MANUAL)
            "insurance" -> navController?.navigate(NavRoute.INSURANCE)

            // Help
            "report" -> navController?.navigate(NavRoute.REPORT_PROBLEM)
            "claim" -> navController?.navigate(NavRoute.MAKE_CLAIM)
            "contact" -> navController?.navigate(NavRoute.CONTACT_US)
            "faq" -> navController?.navigate(NavRoute.FAQ)

            // App
            "terms" -> navController?.navigate(NavRoute.SUBSCRIPTION) /* or NavRoute.TERMS if you have that */
            "privacy" -> navController?.navigate(NavRoute.PAYMENT_METHOD) /* replace with correct route constant */

            // fallback - no-op when navController is null
            else -> { /* no-op */ }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(pageBg),
        contentPadding = PaddingValues(top = 18.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                text = "More",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(start = 18.dp, bottom = 12.dp)
            )
        }

        // Glovebox
        item { SectionHeader(text = "My Glovebox") }
        item {
            GroupCard {
                GroupRow(Icons.Default.ReceiptLong, "Welcome Pack") { handleRowTap("welcome") }
                DividerIndented()
                GroupRow(Icons.Default.Settings, "BMW i5 Manual") { handleRowTap("manual") }
                DividerIndented()
                GroupRow(Icons.Default.Policy, "My Insurance Details") { handleRowTap("insurance") }
            }
        }

        item { Spacer(Modifier.height(18.dp)) }

        // Help
        item { SectionHeader(text = "Help") }
        item {
            GroupCard {
                GroupRow(Icons.Default.ReportProblem, "Report a Problem") { handleRowTap("report") }
                DividerIndented()
                GroupRow(Icons.Default.Settings, "Make a Claim") { handleRowTap("claim") }
                DividerIndented()
                GroupRow(Icons.Default.Phone, "Contact Us") { handleRowTap("contact") }
                DividerIndented()
                GroupRow(Icons.Default.HelpOutline, "Frequently Asked Questions") { handleRowTap("faq") }
            }
        }

        item { Spacer(Modifier.height(18.dp)) }

        // App
        item { SectionHeader(text = "App") }
        item {
            GroupCard {
                ToggleRow(
                    icon = Icons.Default.Notifications,
                    text = "App Notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                DividerIndented()
                GroupRow(Icons.Default.Policy, "Terms & Conditions") { handleRowTap("terms") }
                DividerIndented()
                GroupRow(Icons.Default.Policy, "Privacy Policy") { handleRowTap("privacy") }
            }
        }

        item { Spacer(Modifier.height(22.dp)) }

        // Logout
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                tonalElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .clickable { onLogout() }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LeadingSquareIcon(icon = Icons.Default.Logout)
                    Spacer(Modifier.width(12.dp))
                    Text("Log Out", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/* small reusables - same as before */
@Composable private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(start = 18.dp, bottom = 8.dp)
    )
}

@Composable private fun GroupCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        tonalElevation = 0.dp
    ) { Column { content() } }
}

@Composable private fun GroupRow(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeadingSquareIcon(icon)
        Spacer(Modifier.width(12.dp))
        Text(text = text, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
    }
}

@Composable private fun ToggleRow(icon: ImageVector, text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        LeadingSquareIcon(icon)
        Spacer(Modifier.width(12.dp))
        Text(text = text, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = iOSGreen, uncheckedThumbColor = Color.White, uncheckedTrackColor = Color.LightGray)
        )
    }
}

@Composable private fun LeadingSquareIcon(icon: ImageVector) {
    Surface(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)), color = Color(0xFFEAEAEA)) {
        Box(contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = Color.DarkGray) }
    }
}

@Composable private fun DividerIndented() {
    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp, modifier = Modifier.padding(start = 70.dp))
}

@Preview(showBackground = true)
@Composable
private fun MoreScreenPreview() {
    VoltricTheme {
        MoreScreen(navController = null, onLogout = {})
    }
}

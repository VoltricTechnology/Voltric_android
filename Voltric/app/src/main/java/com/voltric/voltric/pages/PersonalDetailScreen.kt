
@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.voltric.voltric.ui.theme.VoltricTheme

private val PageBackground = Color(0xFFF5F5F7)
private val CardBackground = Color.White
private val SecondaryText = Color(0xFF9E9E9E) // light gray for right-hand values
private val DividerColor = Color(0xFFEDEDED)

@Composable
fun PersonalDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onRowClick: (id: String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Personal Details",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PageBackground
                )
            )
        },
        containerColor = PageBackground,
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(PageBackground)
                .padding(vertical = 12.dp)
        ) {
            // Section: Personal (card)
            SectionHeader(title = "Personal")
            SectionCard {
                InfoRow(label = "Account Holder Name", value = "Peter Gabriel", onClick = null)
                ThinDivider()
                InfoRow(label = "Date of Birth", value = "01/01/1990", onClick = null)
                ThinDivider()
                // Address row with multi-line value and chevron
                InfoRow(
                    label = "Address",
                    value = "123 Liverpool Street, Clifton, Bristol,\nBS8 3BY",
                    onClick = { onRowClick("address") },
                    showChevron = true
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section: Account
            SectionHeader(title = "Account")
            SectionCard {
                InfoRow(
                    label = "Login Email",
                    value = "peter.gab@gmail.com",
                    onClick = { onRowClick("login_email") },
                    showChevron = true
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section: Contact Details
            SectionHeader(title = "Contact Details")
            SectionCard {
                InfoRow(
                    label = "Email",
                    value = "peter.gab@gmail.com",
                    onClick = { onRowClick("contact_email") },
                    showChevron = true
                )
                ThinDivider()
                InfoRow(
                    label = "Phone",
                    value = "07463123473",
                    onClick = { onRowClick("contact_phone") },
                    showChevron = true
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(start = 18.dp, bottom = 8.dp)
    )
}

@Composable
private fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(12.dp),
        color = CardBackground,
        tonalElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(vertical = 6.dp)) {
            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    onClick: (() -> Unit)?,
    showChevron: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            )
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label on the left
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
        )

        // Value on the right
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryText, fontSize = 14.sp),
                modifier = Modifier.padding(end = if (showChevron) 8.dp else 0.dp)
            )

            if (showChevron) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Navigate",
                    tint = SecondaryText,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun ThinDivider() {
    Divider(
        color = DividerColor,
        thickness = 1.dp,
        modifier = Modifier.padding(start = 14.dp)
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PersonalDetailsPreview() {
    VoltricTheme {
       // PersonalDetailsScreen(onBack = {}, onRowClick = {})
    }
}

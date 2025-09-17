

@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import com.voltric.voltric.ui.theme.orange

// page colors used to get the iOS-ish look
private val PageBackground = Color(0xFFF5F5F7)
private val CardBackground = Color.White
private val DividerColor = Color(0xFFEDEDED)
private val SecondaryText = Color(0xFF9E9E9E)
private val ExpiredColor = Color(0xFFFF9800) // orange-like for expired

data class PaymentMethod(
    val id: String,
    val label: String,
    val masked: String,
    val status: PaymentStatus
)

enum class PaymentStatus { DEFAULT, ACTIVE, EXPIRED }

@Composable
fun PaymentMethodScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onAdd: () -> Unit = {},
    onItemClick: (PaymentMethod) -> Unit = {}
) {
    val methods = remember {
        listOf(
            PaymentMethod("p1", "Revolut Visa", "•••• 1234", PaymentStatus.DEFAULT),
            PaymentMethod("p2", "Monzo Visa", "•••• 1234", PaymentStatus.EXPIRED)
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Payment Method", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = onEdit) { Text(text = "Edit", color = MaterialTheme.colorScheme.primary) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PageBackground)
            )
        },
        containerColor = PageBackground,
        modifier = modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(PageBackground)
                .padding(vertical = 12.dp)
        ) {
            // Section title
            Text(
                text = "Payment Methods",
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )

            // Card containing the list of payment methods
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                color = CardBackground,
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 0.dp
            ) {
                Column {
                    // item 1
                    PaymentRow(
                        label = methods[0].label,
                        masked = methods[0].masked,
                        status = methods[0].status,
                        onClick = { onItemClick(methods[0]) }
                    )
                    Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(start = 16.dp))
                    // item 2
                    PaymentRow(
                        label = methods[1].label,
                        masked = methods[1].masked,
                        status = methods[1].status,
                        onClick = { onItemClick(methods[1]) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Add Payment Method (separate card with single row)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                color = CardBackground,
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAdd() }
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Add Payment Method", color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ArrowForwardIos, contentDescription = "Add", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun PaymentRow(
    label: String,
    masked: String,
    status: PaymentStatus,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontWeight = FontWeight.SemiBold, color = Color(0xFF0B0B0B))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = masked, color = SecondaryText)
        }

        // status badge / text
        when (status) {
            PaymentStatus.DEFAULT -> {
                Text(
                    text = "Default",
                    color = SecondaryText,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
            PaymentStatus.ACTIVE -> {
                Text(
                    text = "Active",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 12.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }
            PaymentStatus.EXPIRED -> {
                Text(
                    text = "Expired",
                    color = ExpiredColor,
                    modifier = Modifier.padding(end = 12.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "Open", tint = Color(0xFF9E9E9E), modifier = Modifier.size(18.dp))
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PaymentMethodPreview() {
    VoltricTheme {
        //PaymentMethodScreen()
    }
}

@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.ui.theme.VoltricTheme

private val PageBg = Color(0xFFF5F5F7)
private val CardBg = Color.White
private val Green = Color(0xFF34C759)
private val BlueAction = Color(0xFF007AFF)

@Composable
fun RenewalScreen(onClose: () -> Unit = {}) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Renewal", fontSize = 18.sp, color = Color.Black)
                },
                actions = {
                    TextButton(onClick = onClose) {
                        Text("Close", color = BlueAction)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PageBg)
            )
        },
        containerColor = PageBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(PageBg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Green calendar icon
            Surface(
                shape = CircleShape,
                modifier = Modifier.size(96.dp),
                color = CardBg,
                tonalElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = Green,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Title
            Text(
                text = "Active Subscription",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            // Description
            Text(
                text = "You have six months remaining on your current subscription. " +
                        "We'll reach out to you as the end of your term approaches " +
                        "to discuss your renewal options.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RenewalScreenPreview() {
    VoltricTheme {
        RenewalScreen()
    }
}

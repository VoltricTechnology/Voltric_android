
@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.ui.theme.VoltricTheme

private val PageBackground = Color(0xFFF5F5F7)
private val CardBackground = Color.White
private val DividerColor = Color(0xFFE0E0E0)
private val BlueButton = Color(0xFF007AFF)
private val GreenText = Color(0xFF34C759)

@Composable
fun AdjustMileageScreen(
    currentLimit: Int = 1000,
    currentCost: Int = 750,
    onClose: () -> Unit = {},
    onSubmit: (Int) -> Unit = {}
) {
    var mileage by remember { mutableStateOf(1100) }
    val newCost = (mileage / 1000f * currentCost).toInt() // simple cost scaling

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Adjust Mileage", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {},
                actions = {
                    TextButton(onClick = onClose) { Text("Close", color = BlueButton) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PageBackground)
            )
        },
        containerColor = PageBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(PageBackground)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))

            // Mileage Adjuster
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleButton(icon = Icons.Default.Remove) {
                    if (mileage > 500) mileage -= 100
                }

                Spacer(Modifier.width(24.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "%,d".format(mileage),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Miles per month",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(Modifier.width(24.dp))

                CircleButton(icon = Icons.Default.Add) { mileage += 100 }
            }

            Spacer(Modifier.height(28.dp))

            // Current Monthly Limit
            CardRow(label = "Current Monthly Limit", value = "$currentLimit Miles")

            Spacer(Modifier.height(12.dp))

            // Current & New Monthly Cost
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CardBackground,
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 0.dp
            ) {
                Column {
                    CardRow(label = "Current Monthly Cost", value = "£$currentCost", showDivider = true)
                    CardRow(label = "New Monthly Cost", value = "£$newCost", highlight = true)
                }
            }

            Spacer(Modifier.weight(1f))

            // Request button
            Button(
                onClick = { onSubmit(mileage) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueButton,
                    contentColor = Color.White
                )
            ) {
                Text("Request Mileage Change", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun CircleButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color.Black)
    }
}

@Composable
private fun CardRow(label: String, value: String, showDivider: Boolean = false, highlight: Boolean = false) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, modifier = Modifier.weight(1f), fontWeight = FontWeight.Normal)
            Text(
                value,
                fontWeight = FontWeight.SemiBold,
                color = if (highlight) GreenText else Color.Gray,
                textAlign = TextAlign.End
            )
        }
        if (showDivider) {
            Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(start = 14.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun AdjustMileagePreview() {
    VoltricTheme {
        AdjustMileageScreen()
    }
}

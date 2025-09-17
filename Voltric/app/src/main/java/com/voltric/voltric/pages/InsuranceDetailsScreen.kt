
@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Colors tuned to your app's iOS-like style
private val PageBg = Color(0xFFF2F2F7)
private val CardBg = Color.White
private val DividerColor = Color(0xFFE6E6EA)
private val LabelText = Color(0xFF1C1C1E)
private val ValueText = Color(0xFF0A84FF) // blue link color for contact
private val SecondaryValueText = Color(0xFF8E8E93)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsuranceDetailsScreen(
    navController: NavController? = null,           // pass from NavHost or leave null for preview
    modifier: Modifier = Modifier,
    insurer: String = "NIG Insurance",
    policyNumber: String = "12345678",
    contact: String = "074208274622"
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Insurance Details") },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        containerColor = PageBg
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PageBg)
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Card with rows
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = CardBg,
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 0.dp
            ) {
                Column {
                    InfoRow(label = "Insurer", value = insurer, showDivider = true, valueColor = SecondaryValueText)
                    InfoRow(label = "Policy Number", value = policyNumber, showDivider = true, valueColor = SecondaryValueText)
                    InfoRow(label = "Contact Detail", value = contact, showDivider = false, valueColor = ValueText, clickable = true) {
                        // If you want to handle click (call / copy) add logic here.
                        // e.g. navController?.navigate("call:$contact") or use Intent via activity context.
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    showDivider: Boolean,
    valueColor: Color = SecondaryValueText,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (clickable) Modifier.clickable(onClick = onClick) else Modifier)
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                color = LabelText,
                fontSize = 16.sp
            )

            Text(
                text = value,
                color = valueColor,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (showDivider) {
            Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(start = 14.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun InsuranceDetailsPreview() {
    InsuranceDetailsScreen(navController = null)
}

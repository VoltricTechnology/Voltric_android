
@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.voltric.voltric.ui.theme.VoltricTheme
import com.voltric.voltric.R

// Colors tuned to the iOS-like screenshots
private val PageBg = Color(0xFFF2F2F7)
private val CardBg = Color.White
private val MutedText = Color(0xFF8E8E93)
private val LabelText = Color(0xFF1C1C1E)
private val AccentRed = Color(0xFFE53935) // red for the destructive button
private val AccentGreen = Color(0xFF10B981)

@Composable
fun CancelSubscriptionScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onCancelled: () -> Unit = {}
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var cancelled by remember { mutableStateOf(false) }

    // If the user has already cancelled, show the final "Goodbye" screen
    if (cancelled) {
        CancelledSuccess(onClose = onClose)
        return
    }

    // Main content
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PageBg)
            .padding(vertical = 18.dp)
    ) {
        // Top bar row (simple)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cancel Subscription",
                modifier = Modifier.weight(1f),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Close",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onClose() }
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Warning icon circle
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFEEDEE)), // light red bg
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_warning), // use your warning vector/drawable
                    contentDescription = "Warning",
                    tint = AccentRed,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Are You Sure?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = LabelText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Cancelling your subscription will result in end of your subscription service.",
                color = MutedText,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp),
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // "What Happens After Cancellation" card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            shape = RoundedCornerShape(12.dp),
            color = CardBg,
            tonalElevation = 0.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("What Happens After Cancellation", fontWeight = FontWeight.SemiBold, color = LabelText, modifier = Modifier.weight(1f))

                }

                Spacer(modifier = Modifier.height(12.dp))

                StepRow(number = 1, text = "Your subscription will remain active until 19th March.")
                StepRow(number = 2, text = "On the 19th of March, we'll come round and collect the car and all keys.")
                StepRow(number = 3, text = "After the 19th, you will lose all access to the vehicle's features.")
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Red destructive button
        Button(
            onClick = { showConfirmDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = AccentRed, contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Cancel My Subscription", fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Small link
        Text(
            text = "Unsure? Call Us",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    // open phone dialer/intent in real app
                }
                .padding(6.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp
        )
    }

    // Confirmation dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Are you sure?") },
            text = {
                Text("If you cancel your subscription we will reach out to you and confirm the cancellation.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        cancelled = true
                        onCancelled()
                    }
                ) {
                    Text("Cancel My Subscription", color = AccentRed, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Unsure? Call Us")
                }
            }
        )
    }
}

@Composable
private fun StepRow(number: Int, text: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "$number.",
            color = AccentGreen,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(28.dp)
        )
        Text(
            text = text,
            color = MutedText,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CancelledSuccess(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_sad_face), // supply a sad icon or use vector
            contentDescription = null,
            tint = AccentRed,
            modifier = Modifier
                .size(92.dp)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))

        Text("We're Sorry to See You Go", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = LabelText)
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "If you change your mind, feel free to reach out to us and we can discuss your subscription options.",
            color = MutedText,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 36.dp),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onClose() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp, vertical = 24.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Done", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CancelSubscriptionPreview() {
    VoltricTheme {
        CancelSubscriptionScreen(onClose = { /*close*/ }, onCancelled = { /*done*/ })
    }
}

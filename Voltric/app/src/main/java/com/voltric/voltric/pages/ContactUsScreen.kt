
@file:OptIn(ExperimentalMaterial3Api::class)
package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.voltric.voltric.ui.theme.VoltricTheme

private val PageBg = Color(0xFFF2F2F7)
private val CardBg = Color.White
private val DividerColor = Color(0xFFE5E5EA)
private val LabelText = Color(0xFF1C1C1E)
private val ValueText = Color(0xFF007AFF)

@Composable
fun ContactUsScreen(
    modifier: Modifier = Modifier,
    onLiveChatClicked: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PageBg),
        contentPadding = PaddingValues(vertical = 18.dp, horizontal = 0.dp)
    ) {
        item {
            Text(
                text = "Contact Us",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 18.dp, bottom = 12.dp)
            )
        }

        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                color = CardBg,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    ContactRow(icon = Icons.Default.Phone, label = "Phone", value = "074208274622")
                    Divider(color = DividerColor, thickness = 1.dp)
                    ContactRow(icon = Icons.Default.Email, label = "Email", value = "help@voltric.co.uk")
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                color = CardBg,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLiveChatClicked() }
                        .padding(horizontal = 14.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LeadingSquareIcon(icon = Icons.Default.Chat)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Live Chat With Us", modifier = Modifier.weight(1f), color = LabelText)
                    Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                }
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun ContactRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeadingSquareIcon(icon)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, modifier = Modifier.weight(1f))
        Text(text = value, color = ValueText)
    }
}

@Composable
private fun LeadingSquareIcon(icon: ImageVector) {
    Surface(
        modifier = Modifier.size(40.dp),
        color = Color(0xFFF2F2F7),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Color(0xFF6C6C70), modifier = Modifier.size(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactUsPreview() {
    VoltricTheme { ContactUsScreen() }
}

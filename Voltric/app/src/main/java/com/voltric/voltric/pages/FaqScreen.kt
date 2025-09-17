@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.ui.theme.VoltricTheme

// Colors matching your iOS-like palette
private val PageBg = Color(0xFFF2F2F7)
private val CardBg = Color.White
private val DividerColor = Color(0xFFE6E6EA)
private val TitleColor = Color(0xFF111111)
private val SubtitleColor = Color(0xFF6E6E73)
private val Accent = Color(0xFF007AFF)

data class FaqItem(val question: String, val answer: String)

@Composable
fun FaqScreen(
    faqs: List<FaqItem> = sampleFaqs(),
    modifier: Modifier = Modifier,
    onOpenLink: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // filter list based on query
    val filtered = remember(faqs, query) {
        if (query.isBlank()) faqs
        else {
            val q = query.trim().lowercase()
            faqs.filter {
                it.question.lowercase().contains(q) || it.answer.lowercase().contains(q)
            }
        }
    }

    androidx.compose.foundation.lazy.LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PageBg),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 16.dp, horizontal = 0.dp)
    ) {

        item {
            // Top title
            Text(
                text = "FAQ",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(start = 18.dp, bottom = 12.dp, top = 6.dp),
                color = TitleColor
            )
        }

        item {
            // Search box: rounded white Surface with a BasicTextField inside (no TextFieldDefaults calls)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                color = CardBg,
                shape = RoundedCornerShape(10.dp),
                tonalElevation = 0.dp
            ) {
                Box(modifier = Modifier.padding(vertical = 6.dp)) {
                    SearchBox(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        // top card placeholder (keeps visual spacing like your screenshots)
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(12.dp),
                color = CardBg,
                tonalElevation = 0.dp
            ) {
                Column {
                    if (filtered.isEmpty()) {
                        Text(
                            text = "No results",
                            modifier = Modifier.padding(16.dp),
                            color = SubtitleColor
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(10.dp)) }

        // Individual FAQ rows
        itemsIndexed(filtered) { index, faq ->
            FaqRow(item = faq, showDivider = (index != filtered.lastIndex), onOpenLink = onOpenLink)
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }
    }
}

@Composable
private fun SearchBox(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    // BasicTextField + decorationBox so we can control colors without relying on TextFieldDefaults
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = SolidColor(Accent),
        textStyle = TextStyle(color = TitleColor, fontSize = 16.sp),
        modifier = modifier,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 44.dp)
                    .padding(horizontal = 12.dp)
            ) {
                // leading icon
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = SubtitleColor
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(text = "Search FAQs", color = SubtitleColor)
                    }
                    // the actual editable text
                    innerTextField()
                }
            }
        }
    )
}

@Composable
private fun FaqRow(item: FaqItem, showDivider: Boolean = true, onOpenLink: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.question, fontWeight = FontWeight.SemiBold, color = TitleColor)
                Spacer(modifier = Modifier.height(6.dp))
                if (!expanded) {
                    Text(
                        text = item.answer.take(140).let { if (it.length < item.answer.length) "$it…" else it },
                        fontSize = 13.sp,
                        color = SubtitleColor,
                        maxLines = 2
                    )
                } else {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.answer,
                        fontSize = 14.sp,
                        color = SubtitleColor,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(top = 6.dp)
                    )
                }
            }

            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = if (expanded) "Collapse" else "Expand",
                modifier = Modifier
                    .size(18.dp)
                    .rotate(rotation),
                tint = SubtitleColor
            )
        }

        if (showDivider) {
            androidx.compose.material3.Divider(color = DividerColor, thickness = 1.dp)
        }
    }
}

private fun sampleFaqs() = listOf(
    FaqItem(
        question = "What happens if I lose my car keys?",
        answer = "If you lose your car keys, please immediately contact us at 07123456789. We can advise on the replacement cost and any next steps."
    ),
    FaqItem(
        question = "Can I drive abroad?",
        answer = "Yes — your subscription covers driving abroad within the EU for up to 90 days. If you plan a longer trip, contact support for an extension."
    ),
    FaqItem(
        question = "What happens if I put diesel in instead of electric?",
        answer = "Modern EVs don't accept diesel — putting diesel in an electric car is not applicable. If you meant putting petrol/fuel in a hybrid, please contact support and we will advise."
    ),
    FaqItem(
        question = "How many miles do I have left this month?",
        answer = "Your remaining allowance is visible on the Subscription screen. If you need an increase, go to Adjust Mileage under Subscription."
    ),
    FaqItem(
        question = "Can I add my wife?",
        answer = "Yes, you can add additional drivers on your subscription. Open Add New Driver in the Subscription screen and follow the steps."
    ),
    FaqItem(
        question = "Am I insured to take my car on ferries?",
        answer = "Most ferry crossings are covered but please check the policy terms. Contact support for specific routes or any clarifications."
    ),
    FaqItem(
        question = "Do you have a referral scheme?",
        answer = "Yes — refer a friend and both of you may be eligible for discounts. See the 'Refer a Friend' section in the app for current offers."
    )
)

@Preview(showBackground = true)
@Composable
private fun FaqPreview() {
    VoltricTheme { FaqScreen() }
}



@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voltric.voltric.ui.theme.VoltricTheme
import com.voltric.voltric.ui.theme.orange

/**
 * Full add-driver flow:
 *  - AddDriverScreen -> ConfirmDetailsScreen -> DriverAddedScreen
 *
 * This file provides a simple in-file state navigation, sample validation, and UI that matches the provided mockups.
 */

private val PageBg = Color(0xFFF5F5F7)
private val CardBg = Color.White
private val BlueAction = Color(0xFF007AFF)
private val GreenSuccess = Color(0xFF34C759)

enum class AddDriverRoute { ADD, CONFIRM, DONE }

@Composable
fun AddDriverFlowHost(onFinished: () -> Unit = {}) {
    var route by remember { mutableStateOf(AddDriverRoute.ADD) }

    // form state (lifted so confirm screen can read)
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val dob = remember { mutableStateOf("") }              // simple string "DD/MM/YYYY"
    val licence = remember { mutableStateOf("") }          // licence number
    val frontUploaded = remember { mutableStateOf(false) } // placeholders for uploads
    val backUploaded = remember { mutableStateOf(false) }

    when (route) {
        AddDriverRoute.ADD -> AddDriverScreen(
            firstName = firstName,
            lastName = lastName,
            dob = dob,
            licence = licence,
            frontUploaded = frontUploaded,
            backUploaded = backUploaded,
            onCancel = onFinished,
            onNext = {
                route = AddDriverRoute.CONFIRM
            }
        )

        AddDriverRoute.CONFIRM -> ConfirmDetailsScreen(
            firstName = firstName.value,
            lastName = lastName.value,
            dob = dob.value,
            licence = licence.value,
            currentCost = 750,
            newCost = 875,
            onBack = { route = AddDriverRoute.ADD },
            onConfirm = { route = AddDriverRoute.DONE }
        )

        AddDriverRoute.DONE -> DriverAddedScreen(
            onDone = {
                onFinished()
            }
        )
    }
}

/* ----------------- Add Driver (form) ----------------- */

@Composable
fun AddDriverScreen(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    dob: MutableState<String>,
    licence: MutableState<String>,
    frontUploaded: MutableState<Boolean>,
    backUploaded: MutableState<Boolean>,

    onCancel: () -> Unit,
    onNext: () -> Unit
) {
    val focus = LocalFocusManager.current
    val showError = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Driver", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold) },
                actions = {
                    TextButton(onClick = onCancel) { Text("Close", color = BlueAction) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PageBg)
            )
        },
        containerColor = PageBg
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(PageBg)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // DRIVER NAME
            SectionLabel("Driver Name")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = CardBg,
                tonalElevation = 0.dp
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        placeholder = { Text("First name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        placeholder = { Text("Surname") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // DOB
            SectionLabel("Date of Birth")
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), color = CardBg) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = dob.value,
                        onValueChange = { dob.value = it },
                        placeholder = { Text("DD/MM/YYYY") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Driver must be at least 23 years old.",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Licence number
            SectionLabel("Drivers Licence Number")
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), color = CardBg) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = licence.value,
                        onValueChange = { licence.value = it },
                        placeholder = { Text("16 Digit Number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "The long number at the top of your license",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Uploads
            SectionLabel("Upload Drivers License Photo")
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), color = CardBg) {
                Column {
                    UploadRow(
                        title = "Front of license",
                        uploaded = frontUploaded.value,
                        onClick = { frontUploaded.value = !frontUploaded.value }
                    )
                    Divider(color = Color(0xFFEDEDED))
                    UploadRow(
                        title = "Rear of license",
                        uploaded = backUploaded.value,
                        onClick = { backUploaded.value = !backUploaded.value }
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // Error
            if (showError.value) {
                Text(
                    "Please complete all required fields and upload both images.",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Add Driver button
            Button(
                onClick = {
                    focus.clearFocus()
                    // simple validation
                    val ok = firstName.value.isNotBlank() && lastName.value.isNotBlank() &&
                            dob.value.isNotBlank() && licence.value.length >= 4 && frontUploaded.value && backUploaded.value
                    if (ok) {
                        showError.value = false
                        onNext()
                    } else {
                        showError.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = BlueAction, contentColor = Color.White)
            ) {
                Text("Add Driver", fontSize = 16.sp)
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun UploadRow(title: String, uploaded: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Upload,
            contentDescription = null,
            tint = if (uploaded) Color(0xFF0BBF5D) else MaterialTheme.colorScheme.primary
        )
    }
}

/* ----------------- Confirm Details ----------------- */

@Composable
fun ConfirmDetailsScreen(
    firstName: String,
    lastName: String,
    dob: String,
    licence: String,
    currentCost: Int,
    newCost: Int,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Confirm Details", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = { TextButton(onClick = onBack) { Text("Close", color = BlueAction) } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PageBg)
            )
        },
        containerColor = PageBg
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(PageBg)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // new driver details
            SectionLabel("New Driver")
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), color = CardBg) {
                Column(modifier = Modifier.padding(12.dp)) {
                    ConfirmRow(label = "First name", value = firstName)
                    Divider(color = Color(0xFFEDEDED))
                    ConfirmRow(label = "Surname", value = lastName)
                    Divider(color = Color(0xFFEDEDED))
                    ConfirmRow(label = "Date of Birth", value = dob)
                    Divider(color = Color(0xFFEDEDED))
                    ConfirmRow(label = "License Number", value = licence)
                }
            }

            Spacer(Modifier.height(16.dp))

            // subscription change costs
            SectionLabel("Subscription Change")
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), color = CardBg) {
                Column {
                    CostRow(label = "Current Monthly Cost", value = "£$currentCost", showDivider = true)
                    CostRow(label = "New Monthly Cost", value = "£$newCost", highlight = true)
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = BlueAction, contentColor = Color.White)
            ) {
                Text("Confirm & Add", fontSize = 16.sp)
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ConfirmRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(text = value, color = Color.Gray)
    }
}

@Composable
private fun CostRow(label: String, value: String, showDivider: Boolean = false, highlight: Boolean = false) {
    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = label, modifier = Modifier.weight(1f))
            Text(text = value, color = if (highlight) GreenSuccess else Color.Gray, fontWeight = if (highlight) androidx.compose.ui.text.font.FontWeight.Bold else null)
        }
        if (showDivider) Divider(color = Color(0xFFEDEDED))
    }
}

/* ----------------- Driver Added (success) ----------------- */

@Composable
fun DriverAddedScreen(onDone: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Mileage", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold) },
                actions = { TextButton(onClick = {}) { Text("Close", color = BlueAction) } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = PageBg)
            )
        },
        containerColor = PageBg
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(PageBg)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(56.dp))

            // success icon
            Surface(
                shape = CircleShape,
                modifier = Modifier.size(96.dp),
                color = CardBg,
                tonalElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("✓", color = GreenSuccess, fontSize = 48.sp, textAlign = TextAlign.Center)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("Driver Added", fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "Phil has been added to your subscription. Your new monthly cost will start immediately, and billed at your next billing date.",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth().height(52.dp).clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = BlueAction, contentColor = Color.White)
            ) {
                Text("Done")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

/* ----------------- small helpers ----------------- */

@Composable
private fun SectionLabel(text: String) {
    Text(text = text, modifier = Modifier.padding(bottom = 8.dp), fontSize = 14.sp, color = Color(0xFF222222), fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
}

/* ----------------- Preview ----------------- */

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AddDriverFlowPreview() {
    VoltricTheme {
        AddDriverFlowHost(onFinished = {})
    }
}

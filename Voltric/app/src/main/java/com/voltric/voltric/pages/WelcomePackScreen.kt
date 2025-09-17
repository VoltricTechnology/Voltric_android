@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import com.voltric.voltric.R
import com.voltric.voltric.ui.theme.VoltricTheme

@Composable
fun WelcomePackScreen(
    navController: NavController? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White) // White around the app bar
    ) {
        // iOS-style center aligned top bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Welcome Pack",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
                navigationIconContentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Full background image (already includes text + logo)
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_bg),
                contentDescription = "Welcome image",
                contentScale = ContentScale.FillWidth, // fill horizontally
                modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomePackScreenPreview() {
    VoltricTheme {
        WelcomePackScreen(navController = null)
    }
}

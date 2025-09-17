@file:OptIn(ExperimentalMaterial3Api::class)

package com.voltric.voltric.pages

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.voltric.voltric.R
import com.voltric.voltric.ui.theme.VoltricTheme

/**
 * Screen that shows the car manual image (full-screen style).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarManualScreen(
    navController: NavController? = null,
    modifier: Modifier = Modifier,
    @Suppress("Unused") drawableResId: Int = R.drawable.bmw_bg // replace with your drawable name
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // iOS-like center aligned appbar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "BMW i5 Manual",
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

        // Content area: show manual image. Use vertical scroll so tall images are accessible.
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = "Car manual",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                // you can limit maxHeight if desired:
                // .heightIn(max = 1200.dp)
            )
        }
    }
}

/* ---------- Preview ---------- */
@Preview(showBackground = true)
@Composable
private fun CarManualPreview() {
    VoltricTheme {
        // pass null navController for preview
        CarManualScreen(navController = null, drawableResId = R.drawable.bmw_bg)
    }
}

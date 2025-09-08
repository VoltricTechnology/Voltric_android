package com.voltric.voltric

import com.voltric.voltric.nav.VoltricBottomBar
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycars.MyCarsScreen
import com.voltric.voltric.nav.BottomDest
import com.voltric.voltric.ui.pages.CarAppMainScreen
import com.voltric.voltric.ui.pages.InsightsScreen
import com.voltric.voltric.ui.pages.MoreScreen
import com.voltric.voltric.ui.theme.VoltricTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoltricApp()
        }
    }
}


@Composable
fun VoltricApp() {
    val navController = rememberNavController()

    VoltricTheme {
        Scaffold(
            bottomBar = { VoltricBottomBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BottomDest.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(BottomDest.Home.route) { CarAppMainScreen() }
                composable(BottomDest.MyCars.route) { MyCarsScreen() }
                composable(BottomDest.Services.route) { }
                composable(BottomDest.Insights.route) { InsightsScreen() }
                composable(BottomDest.More.route) { MoreScreen() }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VoltricTheme {
        //Greeting("Android")
    }
}
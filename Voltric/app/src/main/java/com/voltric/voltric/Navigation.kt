package com.voltric.voltric

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycars.MyCarsScreen
import com.voltric.voltric.ui.pages.CarAppMainScreen

@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController = navController, startDestination = "home"){
        composable("home"){
            CarAppMainScreen()
        }

        composable("mycar"){
            MyCarsScreen()
        }

    }

}


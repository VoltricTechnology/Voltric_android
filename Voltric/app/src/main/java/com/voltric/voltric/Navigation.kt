package com.voltric.voltric

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycars.MyCarsScreen
import com.voltric.voltric.nav.NavRoute
import com.voltric.voltric.pages.AddDriverFlowHost
import com.voltric.voltric.pages.AddDriverScreen
import com.voltric.voltric.pages.AdjustMileageScreen
import com.voltric.voltric.pages.PaymentMethodScreen
import com.voltric.voltric.pages.PersonalDetailsScreen
import com.voltric.voltric.pages.RenewalScreen
import com.voltric.voltric.pages.SubscriptionScreen
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
//
//        composable("renewl"){
//            RenewalScreen()
//        }

        /* ---------- Subscription flow + screens ---------- */

        // Subscription main screen (passes navController so rows can navigate)
        composable(NavRoute.SUBSCRIPTION) {
            // SubscriptionScreen must accept NavController parameter
            SubscriptionScreen(navController = navController)
        }

        // Add driver flow (replace with your composable)
        composable(NavRoute.ADD_DRIVER) {
            // If you have a multi-step flow, you can host it here
            AddDriverFlowHost(onFinished = {navController.popBackStack()})
        }

        // Adjust mileage screen
        composable(NavRoute.ADJUST_MILEAGE) {
            AdjustMileageScreen(onClose = { navController.popBackStack() })
        }

        // Renewal screen
        composable(NavRoute.RENEWAL) {
            RenewalScreen(onClose = { navController.popBackStack() })
        }

        // Cancel subscription screen
        composable(NavRoute.CANCEL_SUBSCRIPTION) {
            //CancelSubscriptionScreen(onClose = { navController.popBackStack() })
        }

        // Billing history
        composable(NavRoute.BILLING_HISTORY) {
            //BillingHistoryScreen(onClose = { navController.popBackStack() })
        }

        // Payment method
        composable(NavRoute.PAYMENT_METHOD) {
            PaymentMethodScreen( navController)
        }

        // Personal details
        composable(NavRoute.PERSONAL_DETAILS) {
            PersonalDetailsScreen(navController)
        }
    }

}



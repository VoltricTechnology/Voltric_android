package com.voltric.voltric

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
import com.voltric.voltric.nav.NavRoute
import com.voltric.voltric.pages.ChargingScreen
import com.voltric.voltric.pages.SubscriptionScreen
import com.voltric.voltric.pages.RenewalScreen
import com.voltric.voltric.pages.PaymentMethodScreen
import com.voltric.voltric.pages.AdjustMileageScreen
import com.voltric.voltric.pages.AddDriverFlowHost
import com.voltric.voltric.pages.CancelSubscriptionScreen
import com.voltric.voltric.pages.CarManualScreen
import com.voltric.voltric.pages.ContactUsScreen
import com.voltric.voltric.pages.FaqScreen
import com.voltric.voltric.pages.InsuranceDetailsScreen
import com.voltric.voltric.pages.PersonalDetailsScreen
import com.voltric.voltric.pages.ReportProblemScreen
import com.voltric.voltric.ui.pages.CarAppMainScreen
import com.voltric.voltric.ui.pages.InsightsScreen
import com.voltric.voltric.pages.MoreScreen
import com.voltric.voltric.pages.WelcomePackScreen
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
            bottomBar = { com.voltric.voltric.nav.VoltricBottomBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BottomDest.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Bottom tabs
                composable(BottomDest.Home.route) { CarAppMainScreen() }
                composable(BottomDest.MyCars.route) { ChargingScreen() }
                composable(BottomDest.Services.route) { SubscriptionScreen(navController = navController) }
                composable(BottomDest.Insights.route) { InsightsScreen() }
                composable(BottomDest.More.route) { MoreScreen(navController = navController)}

                // Additional subscription-related routes (modal/detail screens)
                composable(NavRoute.ADD_DRIVER) {
                    // Use a host/wrapper that creates states and starts the flow
                    AddDriverFlowHost(onFinished = {navController.popBackStack()})
                }
                composable(NavRoute.ADJUST_MILEAGE) {
                    AdjustMileageScreen(onClose = { navController.popBackStack() })
                }
                composable(NavRoute.RENEWAL) {
                    RenewalScreen(onClose = { navController.popBackStack() })
                }
                composable(NavRoute.CANCEL_SUBSCRIPTION) {
                    // replace with your cancel screen
                    CancelSubscriptionScreen(onClose = { navController.popBackStack() })
                }
                composable(NavRoute.BILLING_HISTORY) {
                    //BillingHistoryScreen(onClose = { navController.popBackStack() })
                }
                composable(NavRoute.PAYMENT_METHOD) {
                    PaymentMethodScreen(navController)
                }
                composable(NavRoute.PERSONAL_DETAILS) {
                    PersonalDetailsScreen( navController)
                }


                //More Screen Routes
                composable(NavRoute.WELCOME_PACK) {
                    WelcomePackScreen()
                }
                composable(NavRoute.CAR_MANUAL) {
                    CarManualScreen()
                }

                composable(NavRoute.INSURANCE){
                    InsuranceDetailsScreen()
                }

                composable(NavRoute.REPORT_PROBLEM){
                    ReportProblemScreen(navController)
                }
                composable(NavRoute.MAKE_CLAIM){
                   // MakeClaimScreen(navController)
                }
                composable(NavRoute.CONTACT_US){
                    ContactUsScreen()
                }

                composable(NavRoute.FAQ){
                    FaqScreen()
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VoltricTheme { /* VoltricApp() won't preview nav easily */ }
}

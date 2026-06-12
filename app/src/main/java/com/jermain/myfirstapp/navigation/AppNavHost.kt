package com.jermain.myfirstapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jermain.myfirstapp.ui.theme.screens.calculator.CalculatorScreen
import com.jermain.myfirstapp.ui.theme.screens.dashboard.DashScreen
import com.jermain.myfirstapp.ui.theme.screens.homescreen.HomeScreen
import com.jermain.myfirstapp.ui.theme.screens.intent.IntentScreen
import com.jermain.myfirstapp.ui.theme.screens.loginscreen.LoginScreen
import com.jermain.myfirstapp.ui.theme.screens.product.AddProductScreen
import com.jermain.myfirstapp.ui.theme.screens.product.UpdateProductScreen
import com.jermain.myfirstapp.ui.theme.screens.product.ViewProductsScreen
import com.jermain.myfirstapp.ui.theme.screens.registerscreen.RegisterScreen
import com.jermain.myfirstapp.ui.theme.screens.safaricom.SafaricomScreen
import com.jermain.myfirstapp.ui.theme.screens.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }
        composable(ROUTE_LOGIN) {
            LoginScreen(navController)
        }
        composable(ROUTE_DASHBOARD) {
            DashScreen(navController)
        }
        composable(ROUTE_ABOUT) {
            HomeScreen(navController)
        }
        composable(ROUTE_INTENT) {
            IntentScreen(navController)
        }
        composable(ROUTE_CALCULATOR) {
            CalculatorScreen(navController)
        }
        composable(ROUTE_SAFARICOM) {
            SafaricomScreen(navController)
        }
        composable(ROUTE_ADD){
            AddProductScreen(navController)
        }
        composable(ROUTE_UPDATE){
            UpdateProductScreen(navController)
        }
        composable(ROUTE_VIEW){
            ViewProductsScreen(navController)
        }
    }
}

package com.jermain.myfirstapp.ui.theme.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jermain.myfirstapp.R
import com.jermain.myfirstapp.navigation.ROUTE_HOME
import com.jermain.myfirstapp.navigation.ROUTE_SPLASH
import com.jermain.myfirstapp.ui.theme.DarkBackground
import com.jermain.myfirstapp.ui.theme.MyFirstAppTheme
import com.jermain.myfirstapp.ui.theme.PrimaryPurple
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(ROUTE_HOME) {
            popUpTo(ROUTE_SPLASH) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.panther),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp),
//            colorFilter = ColorFilter.tint(PrimaryPurple)
        )

        Spacer(modifier = Modifier.height(30.dp))
        
        Text(
            text = "Calling",
            color = PrimaryPurple,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "The stars are the campfire of those who walked before us",
            color = PrimaryPurple,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MyFirstAppTheme {
        SplashScreen(rememberNavController())
    }
}

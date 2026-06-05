package com.jermain.myfirstapp.ui.theme.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jermain.myfirstapp.R
import com.jermain.myfirstapp.navigation.ROUTE_REGISTER
import com.jermain.myfirstapp.ui.theme.DarkBackground
import com.jermain.myfirstapp.ui.theme.MyFirstAppTheme
import com.jermain.myfirstapp.ui.theme.PrimaryPurple

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {
        Text(
            text = "Voices in The Wind",
            color = PrimaryPurple,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            maxLines = 1
        )
        Text(
            text = "The cries of our ancestors",
            color = PrimaryPurple,
            fontSize = 16.sp
        )
        
        Spacer(modifier = Modifier.height(50.dp))
        
        Image(
            painter = painterResource(R.drawable.africa),
            contentDescription = "africa",
            modifier = Modifier.size(250.dp),
//            colorFilter = ColorFilter.tint(PrimaryPurple)
        )
        
        Spacer(modifier = Modifier.height(60.dp))
        
        Button(
            onClick = { navController.navigate(ROUTE_REGISTER) },
            modifier = Modifier
                .width(300.dp)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "LISTEN",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Homepre() {
    MyFirstAppTheme {
        HomeScreen(rememberNavController())
    }
}

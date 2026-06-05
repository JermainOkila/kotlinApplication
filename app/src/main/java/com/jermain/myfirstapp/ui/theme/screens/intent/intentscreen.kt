package com.jermain.myfirstapp.ui.theme.screens.intent

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SimCard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jermain.myfirstapp.ui.theme.DarkBackground
import com.jermain.myfirstapp.ui.theme.MyFirstAppTheme
import com.jermain.myfirstapp.ui.theme.PrimaryPurple

@Composable
fun IntentScreen(navController: NavHostController) {
    val mContext = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp)
    ) {
        Text(
            text = "System Intents",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(40.dp))

        IntentButton(text = "SIM Toolkit", icon = Icons.Default.SimCard) {
            val intent = mContext.packageManager.getLaunchIntentForPackage("com.android.stk")
            intent?.let { mContext.startActivity(it) }
        }

        Spacer(modifier = Modifier.height(15.dp))

        IntentButton(text = "Dial Number", icon = Icons.Default.Call) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:0143363568".toUri()
            }
            mContext.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(15.dp))

        IntentButton(text = "Send SMS", icon = Icons.AutoMirrored.Filled.Message) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "smsto:0143363568".toUri()
                putExtra("sms_body", "Hello, how was your day?")
            }
            mContext.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(15.dp))

        IntentButton(text = "Camera", icon = Icons.Default.CameraAlt) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            mContext.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(15.dp))

        IntentButton(text = "Email", icon = Icons.Default.Email) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("test@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Hello")
                putExtra(Intent.EXTRA_TEXT, "Content here")
            }
            mContext.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(15.dp))

        IntentButton(text = "Share Content", icon = Icons.Default.Share) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Check this out!")
            }
            mContext.startActivity(Intent.createChooser(intent, "Share via"))
        }
    }
}

@Composable
fun IntentButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text.uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IntentPrev() {
    MyFirstAppTheme {
        IntentScreen(rememberNavController())
    }
}

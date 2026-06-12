package com.jermain.myfirstapp.ui.theme.screens.intent

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import com.jermain.myfirstapp.ui.theme.CardBackground
import com.jermain.myfirstapp.ui.theme.DarkBackground
import com.jermain.myfirstapp.ui.theme.PrimaryPurple

@Composable
fun IntentScreen(navController: NavHostController) {
    val mContext = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "System Intents",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = "Interact with system features",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IntentButton(text = "SIM Toolkit", icon = Icons.Default.SimCard) {
                        val intent = mContext.packageManager.getLaunchIntentForPackage("com.android.stk")
                        intent?.let { mContext.startActivity(it) }
                    }

                    IntentButton(text = "Dial Number", icon = Icons.Default.Call) {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:0143363568".toUri()
                        }
                        mContext.startActivity(intent)
                    }

                    IntentButton(text = "Send SMS", icon = Icons.AutoMirrored.Filled.Message) {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "smsto:0143363568".toUri()
                            putExtra("sms_body", "Hello, how was your day?")
                        }
                        mContext.startActivity(intent)
                    }

                    IntentButton(text = "Camera", icon = Icons.Default.CameraAlt) {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        mContext.startActivity(intent)
                    }

                    IntentButton(text = "Email", icon = Icons.Default.Email) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("test@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "Hello")
                            putExtra(Intent.EXTRA_TEXT, "Content here")
                        }
                        mContext.startActivity(intent)
                    }

                    IntentButton(text = "Share Content", icon = Icons.Default.Share) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "Check this out!")
                        }
                        mContext.startActivity(Intent.createChooser(intent, "Share via"))
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun IntentButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IntentPrev() {
    IntentScreen(rememberNavController())
}

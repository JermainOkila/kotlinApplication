package com.jermain.myfirstapp.ui.theme.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import com.jermain.myfirstapp.data.ProductViewModel
import com.jermain.myfirstapp.models.product
import com.jermain.myfirstapp.navigation.ROUTE_UPDATE
import com.jermain.myfirstapp.ui.theme.CardBackground
import com.jermain.myfirstapp.ui.theme.DarkBackground
import com.jermain.myfirstapp.ui.theme.PrimaryPurple

@Composable
fun ViewProductsScreen(navController: NavController) {
    val context = LocalContext.current
    val productViewModel = ProductViewModel()
    val products = productViewModel.product

    LaunchedEffect(Unit) {
        productViewModel.fetchproduct(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Product Inventory",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryPurple)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(products) { product ->
                        ProductItem(product, navController, productViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: product, navController: NavController, viewModel: ProductViewModel) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image Field
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF252525)),
                contentAlignment = Alignment.Center
            ) {
                val imageUrl = product.imageurl?.replace("upload/", "upload/w_300,f_auto,q_auto/")
                AsyncImage(
                    model = imageUrl,
                    contentDescription = product.productname,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = painterResource(id = android.R.drawable.stat_notify_error)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.productname ?: "No Name", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = product.productcategory ?: "No Category", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = "Price: Ksh ${product.productprice}", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = PrimaryPurple)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Qty: ${product.productquantity}", fontSize = 14.sp, color = Color.Gray)
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = { navController.navigate(ROUTE_UPDATE + "/${product.project_id}") }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryPurple)
                }
                IconButton(onClick = { 
                    product.project_id?.let { viewModel.deleteproduct(it, context) }
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFCF6679))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewProductsScreenPreview() {
    ViewProductsScreen(rememberNavController())
}

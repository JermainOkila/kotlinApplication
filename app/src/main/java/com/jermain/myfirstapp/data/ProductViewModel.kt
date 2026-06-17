package com.jermain.myfirstapp.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import com.jermain.myfirstapp.navigation.ROUTE_VIEW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class ProductViewModel: ViewModel() {
    val Cloudinaryurl = "https://api.cloudinary.com/v1_1/dncydbxdd/image/upload"
    val uploadPreset = "app_image"
    fun uploadProduct(
        imageurl: Uri?,
        productname: String,
        productcategory: String,
        productprice: String,
        productquantity: String,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uploadedImageUrl = imageurl?.let { uploadToCloudinary(context, it) }
                val ref = FirebaseDatabase.getInstance().getReference("Products").push()
                val productdata = mapOf(
                    "id" to ref.key,
                    "productname" to productname,
                    "productcategory" to productcategory,
                    "productprice" to productprice,
                    "productquantity" to productquantity,
                    "imageurl" to uploadedImageUrl
                )

                ref.setValue(productdata).await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Product saved successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEW)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Failed to save",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun uploadToCloudinary(context: Context, uri: Uri): String {
        val contentResolver=context.contentResolver
        val inputStream: InputStream?=contentResolver.openInputStream(uri)
        val fileByte= inputStream?.readBytes() ?:throw Exception("Image failed to raed")
        val requestBody= MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file","image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(),
                    fileByte))
            .addFormDataPart("upload_preset",uploadPreset).build()
        val request= Request.Builder().url(Cloudinaryurl).post(requestBody).build()
        val response= OkHttpClient().newCall(request).execute()
        if (!response.isSuccessful)throw Exception("Upload failed")
        val responseBody=response.body?.string()
        val secureUrl= Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?:"")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("failed to get image url")


    }
}


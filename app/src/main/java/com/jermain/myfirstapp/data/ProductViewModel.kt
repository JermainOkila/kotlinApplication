package com.jermain.myfirstapp.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import com.jermain.myfirstapp.models.product
import com.jermain.myfirstapp.navigation.ROUTE_VIEW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
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
                fileByte.toRequestBody("image/*".toMediaTypeOrNull()))
            .addFormDataPart("upload_preset",uploadPreset).build()
        val request= Request.Builder().url(Cloudinaryurl).post(requestBody).build()
        val response= OkHttpClient().newCall(request).execute()
        if (!response.isSuccessful)throw Exception("Upload failed")
        val responseBody=response.body?.string()
        val secureUrl= Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?:"")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("failed to get image url")


    }
private val _product = mutableStateListOf<product>()
val product: List<product> = _product  
    
    fun fetchproduct(context: Context){ 
        val ref = FirebaseDatabase.getInstance().getReference("product")
        ref.get().addOnSuccessListener { snapshot -> 
            _product.clear()
            for (child in snapshot.children){
                val item = child.getValue(com.jermain.myfirstapp.models.product::class.java)
                item?.let { 
                    val productWithId = it.copy(project_id = child.key)
                    _product.add(productWithId) 
                }
            }
        }.addOnFailureListener {
            Toast.makeText(context,"Failed to load products", Toast.LENGTH_LONG).show()
        }
    }
    fun deleteproduct(productId: String,context: Context){
        val ref = FirebaseDatabase.getInstance()
            .getReference("product").child(productId)
        ref.removeValue().addOnSuccessListener {
            _product.removeAll { it.project_id == productId }
        }.addOnFailureListener {
            Toast.makeText(context,"Product not deleted", Toast.LENGTH_LONG).show()
        }
    }
    
    
}


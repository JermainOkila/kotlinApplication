package com.jermain.myfirstapp.models

import com.jermain.myfirstapp.R

data class Product(
    val name: String = "",
    val category: String = "",
    val price: String = "",
    val quantity: String = "",
    val description: String = "",
    val id: String = "",
    val imageRes: Int = R.drawable.panther // Placeholder
)

package org.sussanacode.groceryappapi.model

data class Product(
    val subId: Int,
    val productID: String,
    val productName: String,
    val productimage: String,
    val price: Double
)

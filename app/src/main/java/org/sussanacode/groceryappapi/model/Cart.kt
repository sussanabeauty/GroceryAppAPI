package org.sussanacode.groceryappapi.model

data class Cart(
    val cartID: Long,
    val productID: String,
    val productname: String,
    var quantity: Int,
    val productImage: String,
    var productprice: Double,
)


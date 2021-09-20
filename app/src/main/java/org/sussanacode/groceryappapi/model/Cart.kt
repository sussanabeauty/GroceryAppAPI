package org.sussanacode.groceryappapi.model

data class Cart(
    //val cartD: Long,
    val productID: Long,
    val productname: String,
    var quantity: Int,
    val productImage: String,
    val productprice: Double
)

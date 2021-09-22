package org.sussanacode.groceryappapi.model

data class ShippingAddress(
    val addressID: Long,
    val name: String,
    val mobile: String,
    val email: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: String

)

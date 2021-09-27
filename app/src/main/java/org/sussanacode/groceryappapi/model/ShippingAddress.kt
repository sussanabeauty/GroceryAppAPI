package org.sussanacode.groceryappapi.model

import java.io.Serializable

data class ShippingAddress(
    val addressID: Long,
    var name: String,
    var mobile: String,
    var email: String,
    var houseNo: Int,
    var address: String,
    var city: String,
    var state: String,
    var zip: String,
    var isPrimary: Int
):Serializable

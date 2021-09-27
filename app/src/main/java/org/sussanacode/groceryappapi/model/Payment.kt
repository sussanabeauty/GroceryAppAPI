package org.sussanacode.groceryappapi.model

import java.io.Serializable

data class Payment(
    val cardID: Long,
    val nameoncard: String,
    val cardNumnber: Long,
    val expirationDt: String,
    val code: String,
    var isPrimary: Int
):Serializable

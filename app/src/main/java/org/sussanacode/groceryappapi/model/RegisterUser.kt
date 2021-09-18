package org.sussanacode.groceryappapi.model

data class RegisterUser(
    val id: Long,
    val firstname: String,
    val mobile: Int,
    val username: String,
    val password: String,
    val createdAt: Long = System.currentTimeMillis()
)

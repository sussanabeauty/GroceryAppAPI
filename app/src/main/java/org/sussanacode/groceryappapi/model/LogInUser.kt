package org.sussanacode.groceryappapi.model

data class LogInUser(val username: String, val password: String, val loginDT: Long = System.currentTimeMillis())

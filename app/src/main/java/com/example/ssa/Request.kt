package com.example.ssa


data class RegisterRequest(
    val user_name: String,
    val password: String,
    val mail: String
    //val group_id: String
)

data class LoginRequest(
    val mail: String,
    val password: String
)


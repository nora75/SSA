package com.example.ssa

import java.io.File


data class RegisterRequest(
    val user_name: String,
    val password: String,
    val mail: String,
    val group_id: String
)

data class LoginRequest(
    val mail: String,
    val password: String
)

data class WriteRequest(
    val id:String,
    val data_name:String,
    val data_type:String
)

data class MultSampleRequest(
//    val data_name: String,
//    val data_type: String,
    val title:String,
    val user_id:String
)

package com.example.ssa

import android.widget.ImageView
import android.widget.TextView

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

data class LogoutRequest(
    val password: String
)

data class Change_group(
    val group_id: String,
    val password: String
)

data class ProtoTypeData(
    val names: String,
    var title: String,
    val data_type: Int
)

data class SampleViewHolder(
    val imageView: ImageView,
    val text1: TextView,
    val text2: TextView
)

data class ChangeGroup(
    val user_id: String
)

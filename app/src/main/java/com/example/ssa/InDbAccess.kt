package com.example.ssa

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


class InDbAccess : Activity() {

    val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)

}
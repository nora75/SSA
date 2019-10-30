package com.example.ssa

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


class InDbAccess : Activity() {
    val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
    val editor = dataStore.edit()

    fun register(user_id:Int,group_id:String){
        editor.putInt("USER_ID",user_id)
        editor.putString("GROUP_ID",group_id)
        editor.apply()
    }

    fun login(Address:String,password:String){
        editor.putString("Address", Address)
        editor.putString("Pass", password)
        editor.apply()
    }

    fun write(){

    }

    fun listen(){

    }

    fun look(){

    }
}
package com.example.ssa

//import android.app.Activity
//import android.content.Context
//import android.content.SharedPreferences

/*


class InDbAccess {
    val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
    val editor = dataStore.edit()

    fun register(user_id:Int,group_id:String){
        editor.putInt("USER_ID",user_id)
        editor.putString("GROUP_ID",group_id)
        editor.apply()
    }

    fun login(Address:String,password:String){
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val editor = dataStore.edit()
        editor.putString("Address", Address)
        editor.putString("Pass", password)
        editor.apply()
    }

    private fun write(){
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val editor = dataStore.edit()
        var user_id = dataStore.getString("USER_ID","null")
        var group_id = dataStore.getString("GROUP_ID","null")
        var pass = dataStore.getString("Pass","null")
    }

    fun listen(){

    }

    fun look(){

    }

}*/
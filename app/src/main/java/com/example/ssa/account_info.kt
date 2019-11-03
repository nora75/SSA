package com.example.ssa

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class account_info : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        (findViewById(R.id.user_password) as TextView).setText(GetPass().toString())

        (findViewById(R.id.user_name) as TextView).setText(GetName())

        (findViewById(R.id.user_mailaddress) as TextView).setText(GetAddress())

        (findViewById(R.id.user_group_id) as TextView).setText(GetGroupId())
    }
    fun GetPass():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val password = dataStore.getString("Pass","")
        return password
    }

    fun GetName():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val name = dataStore.getString("USER_NAME","")
        return name
    }

    fun GetAddress():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val mailaddress = dataStore.getString("Address","")
        return mailaddress
    }

    fun GetGroupId():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val groupid = dataStore.getString("GROUP_ID","")
        return groupid
    }

}

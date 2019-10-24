package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_option.*
import java.io.*
import java.lang.reflect.Method
import java.nio.charset.Charset

class option : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        account_info_button.setOnClickListener{
            var info_intent = Intent(this,account_info::class.java)
            startActivity(info_intent)
        }
        make_group.setOnClickListener{
            var makegroup_intent = Intent(this,add_group::class.java)
            startActivity(makegroup_intent)
        }
        help.setOnClickListener {

        }
        logout.setOnClickListener {
            val dataStore: SharedPreferences = getSharedPreferences("Confirm_Login", Context.MODE_PRIVATE)
            val editor = dataStore.edit()
            editor.putString("Address","")
            editor.putString("Pass","")
            editor.commit()
            finish()
        }

        sample.setOnClickListener {
            val user_id = "1111"
            "http://34.83.80.2:8000/users/${user_id}"
                .httpDelete()
                .responseString{ request, response, result ->
                    when(result){
                        is Result.Failure ->{
                            val ex = result.getException()
                        }
                        is Result.Success ->{
                            val data = result.get()
                        }
                    }
                }
        }
    }
}


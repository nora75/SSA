package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_option.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.*
import java.lang.reflect.Method
import java.nio.charset.Charset

class option : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        val header : HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        account_info_button.setOnClickListener{
            var info_intent = Intent(this,account_info::class.java)
            startActivity(info_intent)
        }
        make_group.setOnClickListener{
            //var makegroup_intent = Intent(this,add_group::class.java)
            //startActivity(makegroup_intent)
            var user_id = 1111
            val requestAdapter = moshi.adapter(Change_group::class.java)
            var request = Change_group(
                group_id = "group",
                password = "group"
            )

            "http://34.83.80.2:8000/users/${user_id}"
                .httpPost()
                .header(header)
                .body(requestAdapter.toJson(request))
                .responseString{ request, response, result ->
                    when(result){
                        is Result.Failure ->{
                            val ex = result.getException()
                            Log.d("error_msg","${ex.toString()}")
                            Toast.makeText(this,"しっぱい",Toast.LENGTH_LONG).show()
                        }
                        is Result.Success ->{
                            val data = result.get()
                            Toast.makeText(this,"成功",Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
        help.setOnClickListener {

        }
        //ログアウトするための機能
        //
        logout.setOnClickListener {
            val dataStore: SharedPreferences = getSharedPreferences("Confirm_Login", Context.MODE_PRIVATE)
            val editor = dataStore.edit()
            editor.putString("Address","")
            editor.putString("Pass","")
            editor.commit()
            finish()
        }
        sample.setOnClickListener {
            //header:ヘッダー
            val header : HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
            //url:基本的なURL
            val requestAdapter = moshi.adapter(LogoutRequest::class.java)

            val LogoutRequest = LogoutRequest(
                password = "group-"
            )
            val user_id = "1111"
            "http://34.83.80.2:8000/users/${user_id}"
                .httpDelete()
                .header()
                .body(requestAdapter.toJson(LogoutRequest), Charset.defaultCharset())
                .responseString{ request, response, result ->
                    when(result){
                        is Result.Failure ->{
                            val ex = result.getException()
                            Log.d("error_msg","${ex.toString()}")
                            Toast.makeText(this,"しっぱい",Toast.LENGTH_LONG).show()
                        }
                        is Result.Success ->{
                            val data = result.get()
                            Toast.makeText(this,"成功",Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
}


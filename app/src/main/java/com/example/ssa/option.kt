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
            val info_intent = Intent(this,account_info::class.java)
            startActivity(info_intent)
        }
        //グループ変更機能
        make_group.setOnClickListener{
            val intent = Intent(this,add_group::class.java)
            startActivity(intent)
            /*
            val requestAdapter = moshi.adapter(Change_group::class.java)
            val changegroup = Change_group(
                group_id = sh_group_id(),
                password = sh_pass_id()
            )
            "http://34.83.80.2:50112/users"
                .httpPost()
                .header(header)
                .body(requestAdapter.toJson(changegroup), Charset.defaultCharset())
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure<*> -> {
                            val ex = result.getException()
                            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                        }
                        is Result.Success<*> -> {
                            Toast.makeText(this,"グループIDの変更が完了しました",Toast.LENGTH_LONG).show()
                        }
                    }
                }
             */
        }
        //ログアウトするための機能
        //
        logout.setOnClickListener {
            val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val editor = dataStore.edit()
            editor.putString("Pass","")
            editor.putString("Address","")
            //テストデータ UserID,UserName,GroupID,DataName,ImageName,Title,DataType
            UserID.removeAll(UserID)
            names.removeAll(names)
            GroupID.removeAll(GroupID)
            dataNameList.removeAll(dataNameList)
            ImageName.removeAll(ImageName)
            viewtitle.removeAll(viewtitle)
            //date.removeAll (date)
            data_type.removeAll(data_type)
            editor.apply()
            finish()
        }
    }
    private fun sh_user_id():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val user_id = dataStore.getString("USER_ID","null")
        return user_id
    }

    private fun sh_group_id():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val group_id = dataStore.getString("GROUP_ID","null")
        return group_id
    }

    private fun sh_pass_id():String{
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val pass = dataStore.getString("Pass","null")
        return pass
    }
}
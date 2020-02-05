package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_add_group.*
import kotlinx.android.synthetic.main.activity_register.*
import java.nio.charset.Charset
import javax.xml.transform.Result

/*
*グループ変更機能
* 使用用途
* 間違えてGroup_idを入れずに登録してしまった場合に、入れ直す機能
 */

/*
* メソッド
* Change_group_id
*
*
 */
class add_group:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        //header:ヘッダー
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        //url:基本的なUR
        add_groupid_button.setOnClickListener{
            val requestAdapter = moshi.adapter(Change_group::class.java)

            if(emptyCheck()) {

                val changegroup = Change_group(
                    group_id = "sampleGroupId",
                    password = sh_pass_id()
                )

                "http://34.83.80.2:50113/users/${sh_user_id()}"
                    .httpPost()
                    .header(header)
                    .body(requestAdapter.toJson(changegroup), Charset.defaultCharset())
                    .responseString { request, response, result ->
                        when (result) {
                            is com.github.kittinunf.result.Result.Failure -> {
                                val ex = result.getException()
                                Log.d("error", ex.toString())
                                Toast.makeText(this, "変更に失敗しました", Toast.LENGTH_LONG).show()
                            }
                            is com.github.kittinunf.result.Result.Success -> {
                                sh_group_id(GetGroupId())
                                Toast.makeText(this, "グループを変更を完了しました", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
            }
            else{
                Toast.makeText(this,"グループIDを入力してください",Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun sh_user_id(): Int {
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val user_id = dataStore.getInt("USER_ID", 0)
        return user_id
    }

    private fun sh_group_id(groupid:String) {
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val editor = dataStore.edit()
        editor.putString("GROUP_ID", groupid)
        editor.apply()
    }

    private fun sh_pass_id(): String {
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val pass = dataStore.getString("Pass", "")
        return pass
    }

    private fun GetGroupId(): String {
        val group_id = findViewById(R.id.add_group_id) as EditText
        return group_id.text.toString()
    }

    private fun emptyCheck():Boolean{
        val pare = GetGroupId()
        if(pare.count()==0){
            return false
        }
        return true
    }
}
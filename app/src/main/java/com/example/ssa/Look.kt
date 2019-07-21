package com.example.ssa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpUpload
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.File

class Look : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look)

        //val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        //val requestAdapter = moshi.adapter(DataRequest::class.java)
        //val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")

        //val datarequest = DataRequest(user_id = "1111")
        val group_id = 1111
        "http://34.83.80.2:8000/group/$group_id"
            .httpGet(listOf("user_id" to "537829"))
            .response{request, respones, result ->
                when(result){
                    is Result.Failure -> {
                        Toast.makeText(this,"失敗しました", Toast.LENGTH_LONG).show()
                        val ex = result.getException()
                        Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                    }
                    is Result.Success -> {
                        val ex = result.get()
                        Toast.makeText(this,"成功しました", Toast.LENGTH_LONG).show()
                        Toast.makeText(this,ex.get(1).toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }


    }
}

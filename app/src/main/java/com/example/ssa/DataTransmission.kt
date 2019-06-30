package com.example.ssa

import android.widget.Toast
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.nio.charset.Charset
import com.example.ssa.RegisterRequest

class DataTransmission(){
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    //header:ヘッダー
    val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
    //url:基本的なURL
    var baseurl = "http://localhost:8000/"

    fun Regist(sampleRequest:RegisterRequest)
    {
        val requestAdapter = moshi.adapter(RegisterRequest::class.java)
        //
        "http://10.0.2.2:8000/Registration"
            .httpPost()
            .header(header)
            .body(requestAdapter.toJson(sampleRequest), Charset.defaultCharset())
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        //Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                    }
                    is Result.Success -> {
                        val data = result.get()
                        val res = moshi.adapter(RegisterRespone::class.java).fromJson(data)
                        //Toast.makeText(this,res?.group_id.toString(),Toast.LENGTH_LONG).show()
                        //Toast.makeText(this,"アカウント作成成功",Toast.LENGTH_LONG).show()
                        //finish()
                    }
                }
            }
    }

    fun Login(loginRequest:LoginRequest)
    {
        val requestAdapter = moshi.adapter(LoginRequest::class.java)
        "http://10.0.2.2:8000/Login"
            .httpPost()
            .header(header)
            .body(requestAdapter.toJson(loginRequest), Charset.defaultCharset())
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        //Toast.makeText(, ex.toString(), Toast.LENGTH_LONG).show()
                    }
                    is Result.Success -> {
                        val data = result.get()
                        val res = moshi.adapter(LoginRespone::class.java).fromJson(data)
                        if(res?.boolean!!){
                        }
                        //Toast.makeText(, "seikou", Toast.LENGTH_LONG).show()
                        //Toast.makeText(this,requestAdapter.fromJson(data), Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

}
/*"http://localhost:8000/"
.httpGet()
.responseString { request, response, result ->
    when (result) {
        is Result.Failure -> {
            val ex = result.getException()
        }
        is Result.Success -> {
            val data = result.get()
        }
    }
}*/
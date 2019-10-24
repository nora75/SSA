package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.nio.charset.Charset

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Login_button.setOnClickListener{

            val loginRequest = LoginRequest(
                mail = GetMailAddress1() + "@" + GetMailAddress2(),
                password = GetPassWord()
            )

            when(AllCheck()) {
                1 ->{
                    Toast.makeText(this,"入力されていない項目があります",Toast.LENGTH_LONG).show()
                }

                2 ->{
                    Toast.makeText(this,"四桁の数字を入力してください",Toast.LENGTH_LONG).show()
                }

                0 -> {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    //header:ヘッダー
                    val header : HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
                    //url:基本的なURL
                    val requestAdapter = moshi.adapter(LoginRequest::class.java)
                    "http://34.83.80.2:8000/Login"
                        .httpPost()
                        .header(header)
                        .body(requestAdapter.toJson(loginRequest), Charset.defaultCharset())
                        .responseString { request, response, result ->
                            when (result) {
                                is Result.Failure -> {
                                    val ex = result.getException()
                                    Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                                }
                                is Result.Success -> {
                                    val data = result.get()
                                    Toast.makeText(this, "正常", Toast.LENGTH_LONG).show()
                                    val res = data.toBoolean()
                                    Toast.makeText(this, res.toString(), Toast.LENGTH_LONG).show()
                                    //val res = moshi.adapter(LoginRespone::class.java).fromJson(data)
                                    if (!res) {
                                        val dataStore: SharedPreferences =
                                            getSharedPreferences(
                                                "Confirm_Login",
                                                Context.MODE_PRIVATE
                                            )
                                        val editor = dataStore.edit()
                                        editor.putString("Address", GetMailAddress1())
                                        editor.putString("Pass", GetPassWord())
                                        editor.commit()
                                        Toast.makeText(this, "ログインに成功しました", Toast.LENGTH_LONG)
                                            .show()
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "ログインに失敗しました。再度入力してください",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }
                }
            }
        }

        Register_button.setOnClickListener{
            val register = Intent(this,Register::class.java)
            startActivity(register)
        }
    }
    private fun GetMailAddress1(): String {
        var mail_address = findViewById(R.id.user_mailaddress3) as EditText
        return mail_address.text.toString()
    }

    private fun GetMailAddress2():String{
        var mail_address = findViewById(R.id.user_mailaddress4) as EditText
        return mail_address.text.toString()
    }

    private fun GetPassWord(): String {
        var password = findViewById(R.id.user_password) as EditText
        return password.text.toString()
    }

    private fun AllCheck():Int{
        if(EmptyCheck()>0){
            return 1
            //入力されていない項目があります
        }
        if(NumCheck()>0){
            return 2
            //四桁の数字を入力してください
        }
        return 0
    }

    private fun EmptyCheck(): Int {
        var SpaceCheck = arrayListOf(GetMailAddress1(),GetMailAddress2(),GetPassWord())
        var checkpoint = 0

        for(checkitem in SpaceCheck){
            if(checkitem.isEmpty()){
                checkpoint++ //何も入っていないときに一ずつたす。
            }
        }
        return checkpoint //一以上ならなんか入ってない項目がある
    }

    private fun NumCheck(): Int {
        var pass = GetPassWord()

        if(pass.length!=4){
            return 1 //error
        }
        return 0 //true
    }
}

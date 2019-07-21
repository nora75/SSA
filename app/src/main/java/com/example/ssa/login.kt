package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
            var checknum = 0
            if(checknum != EmptyCheck()){
                Toast.makeText(this, "入力されていない項目があります", Toast.LENGTH_LONG).show()
            }
            if(checknum != NumCheck()){
                Toast.makeText(this, "パスワードを数字4桁で指定してください", Toast.LENGTH_LONG).show()
            }
            else {
                //dbにアクセスするためのコードを書く
                /*if (checknum != CompareAddress(GetMailAddress())) {
                    Toast.makeText(this, "アドレスが一致しません", Toast.LENGTH_LONG).show()
                }
                if (checknum != CompareNumber(GetPassWord())) {
                    Toast.makeText(this, "パスワードが一致しません", Toast.LENGTH_LONG).show()
                }*/
                val loginRequest = LoginRequest(
                    mail = GetMailAddress(),
                    password = GetPassWord()
                )
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                //header:ヘッダー
                val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
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
                                Toast.makeText(this,"正常",Toast.LENGTH_LONG).show()
                                val res = data.toBoolean()
                                Toast.makeText(this,res.toString(),Toast.LENGTH_LONG).show()
                                //val res = moshi.adapter(LoginRespone::class.java).fromJson(data)
                                if(!res){
                                    val dataStore: SharedPreferences = getSharedPreferences("Confirm_Login", Context.MODE_PRIVATE)
                                    val editor = dataStore.edit()
                                    editor.putString("Address",GetMailAddress())
                                    editor.putString("Pass",GetPassWord())
                                    editor.commit()
                                    Toast.makeText(this, "ログインに成功しました", Toast.LENGTH_LONG).show()
                                    finish()
                                }
                                else {
                                    Toast.makeText(this,"ログインに失敗しました。再度入力してください",Toast.LENGTH_LONG).show()
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
    private fun GetMailAddress(): String {
        var mail_address = findViewById(R.id.user_mailaddress) as EditText
        return mail_address.text.toString()
    }

    private fun GetPassWord(): String {
        var password = findViewById(R.id.user_password) as EditText
        return password.text.toString()
    }
    private fun EmptyCheck(): Int {
        var SpaceCheck = arrayListOf(GetMailAddress(),GetPassWord())
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

    private fun CompareNumber(password: String): Int{
        val pass = "1234"
        if(pass.equals(password)){
            return 0 //true
        }
        return 1 //error
    }

    private fun CompareAddress(value: String): Int{
        val text = "uemura_a@a.a"
        if(value.equals(text)){
            return 0 //true
        }
        return 1 //error
    }

}

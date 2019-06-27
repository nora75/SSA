package com.example.ssa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_register.*
import java.nio.charset.Charset

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener{
            var checknum = 0
            if(checknum != EmptyCheck()){
                Toast.makeText(this, "入力されていない項目があります", Toast.LENGTH_LONG).show()
            }
            if(checknum != NumCheck()){
                Toast.makeText(this, "パスワードを数字4桁で指定してください", Toast.LENGTH_LONG).show()
            }
            if(checknum !=PassCheck()){
                Toast.makeText(this, "パスワードが同一じゃない", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Dbに登録させに行きたい", Toast.LENGTH_LONG).show()
                //dbにアクセスするためのコードを書く
                //通信
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val requestAdapter = moshi.adapter(SampleRequest::class.java)
                val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")

                val sampleRequest = SampleRequest(
                    user_name = GetName(),
                    password = GetPassWord(),
                    mail = GetMailAddress(),
                    group_id = GetGroupID()
                )

                "http://10.0.2.2:8000/Registration"
                    .httpPost()

                    .header(header)
                    .body(requestAdapter.toJson(sampleRequest), Charset.defaultCharset())
                    .responseString { request, response, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                            }
                            is Result.Success -> {
                                val data = result.get()
                                Toast.makeText(this,"seikou",Toast.LENGTH_LONG).show()
                                //Toast.makeText(this,requestAdapter.fromJson(data), Toast.LENGTH_LONG).show()
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
                //Fuel.post("/Registration").body(requestAdapter.toJson(sampleRequest)).responseString{ request: Request, response: Response, result: Result<String, FuelError> ->
                //   Log.d("d","real fuck")
                //}

                //Fuel.get("http://10.0.2.2:8000/Login")
                //val(data,_) = result
                //val res = requestAdapter.fromJson(data)
                Toast.makeText(this, "finish", Toast.LENGTH_LONG).show()
            }
        }
    }
    //メールアドレスの入力欄に入力されているモノを取得する
    private fun GetMailAddress(): String {
        var mail_address = findViewById(R.id.user_mailaddress) as EditText
        return mail_address.text.toString()
    }

    //名前の入力欄に入力されているモノを取得する
    private fun GetName(): String {
        var user_name = findViewById(R.id.user_name) as EditText
        return user_name.text.toString()
    }

    //パスワードの入力欄に入力されているモノを取得する
    private fun GetPassWord(): String {
        var user_pass = findViewById(R.id.user_password) as EditText
        return user_pass.text.toString()
    }

    private fun GetPassWordConfirm() :String{
        var user_pass2 = findViewById(R.id.user_password_confirm) as EditText
        return user_pass2.text.toString()
    }

    //グループIDの入力欄に入力されているモノを取得する
    private fun GetGroupID(): String {
        var group_id = findViewById(R.id.user_group_id) as EditText
        return group_id.text.toString()
    }

    private fun EmptyCheck(): Int {
        var SpaceCheck = arrayListOf(GetMailAddress(),GetName(),GetPassWord())
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
            return 1
        }
        return 0
    }

    private fun PassCheck(): Int{
        //不一致
        if(!GetPassWord().equals(GetPassWordConfirm())){
            Toast.makeText(this,"パスチェック",Toast.LENGTH_LONG).show()
            return 1
        }
        //一致
        else
            return 0
    }
}


data class SampleRequest(
    val user_name: String,
    val password: String,
    val mail: String,
    val group_id: String
    )

data class ResponseData(
    val meta: Meta,
    val id_data: IdData
)

data class Meta(
    val status: Int,
    val message: String
)

data class IdData(
    val id: Int,
    val group_id: String
)
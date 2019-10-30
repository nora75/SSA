package com.example.ssa

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_register.*
import java.nio.charset.Charset

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener {
            //dbにアクセスするためのコードを書く
            //通信
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val requestAdapter = moshi.adapter(RegisterRequest::class.java)
            val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")

            val sampleRequest = RegisterRequest(
                user_name = GetName(),
                password = GetPassWord(),
                mail = GetMailAddress1() + "@" + GetMailAddress2(),
                group_id = GetGroupID()
            )
            when (AllCheck()) {
                1 ->{
                    Toast.makeText(this,"入力されていない項目があります",Toast.LENGTH_LONG).show()
                }

                2 ->{
                    Toast.makeText(this,"四桁の数字を入力してください",Toast.LENGTH_LONG).show()
                }

                3 ->{
                    Toast.makeText(this,"パスワードが一致していません",Toast.LENGTH_LONG).show()
                }

                0 -> {
                    "http://34.83.80.2:8000/Registration"
                        .httpPost()
                        .header(header)
                        .body(requestAdapter.toJson(sampleRequest), Charset.defaultCharset())
                        .responseString { request, response, result ->
                            when (result) {
                                is Result.Failure<*> -> {
                                    val ex = result.getException()
                                    Log.d("error","$ex.toString()")
                                    Toast.makeText(this, "登録情報をもう一度確認してください", Toast.LENGTH_LONG).show()
                                }
                                is Result.Success<*> -> {
                                    val data = result.get()
                                    val res = moshi.adapter(RegisterRespone::class.java).fromJson(data)
                                    //確認用トースト
                                    /*Toast.makeText(
                                        this,
                                        //res?.group_id.toString(),
                                        res?.user_id.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    */
                                    /*
                                    val dataStore: SharedPreferences =
                                        getSharedPreferences(
                                            "USER_DATA",
                                            Context.MODE_PRIVATE
                                        )
                                    val editor = dataStore.edit()
                                    editor.putInt("USER_ID", res?.user_id!!.toInt())
                                    editor.putString("GROUP_ID", res?.group_id.toString())
                                    editor.apply()
                                    */

                                    Toast.makeText(this, "アカウント作成成功", Toast.LENGTH_LONG).show()
                                    finish()
                                }
                            }
                        }
                }
            }
        }
    }

    //メールアドレスの入力欄に入力されているモノを取得する
    private fun GetMailAddress1(): String {
        var mail_address = findViewById(R.id.user_mailaddress1) as EditText
        return mail_address.text.toString()
    }

    private fun GetMailAddress2():String{
        var mail_address = findViewById(R.id.user_mailaddress2) as EditText
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
    //すべてのチェックを確認するメソッド
    //flase check時に一つでも通らなかった時
    //true すべてOK
    private fun AllCheck():Int{
        if(EmptyCheck()>0){
            return 1
            //入力されていない項目があります
        }
        if(NumCheck()>0){
            return 2
            //四桁の数字を入力してください
        }
        if (PassCheck()>0){
            return 3
            //パスワードが一致していません
        }
        return 0
    }

    //数値が入っているかのチェック
    private fun EmptyCheck(): Int {
        var SpaceCheck = arrayListOf(GetMailAddress1(),GetMailAddress2(),GetName(),GetPassWord())
        var checkpoint = 0

        for(checkitem in SpaceCheck){
            if(checkitem.isEmpty()){
                checkpoint++ //何も入っていないときに一ずつたす。
            }
        }
        return checkpoint //一以上ならなんか入ってない項目がある
    }
    //数値が4桁あるのを確認する機能
    private fun NumCheck(): Int {
        var pass = GetPassWord()

        if(pass.length!=4){
            return 1
        }
        return 0
    }
    //パスワードを二回入力し、間違えがないかチェックする機能
    private fun PassCheck(): Int{
        //不一致
        if(!GetPassWord().equals(GetPassWordConfirm())){
            return 1
        }
        //一致
        else
            return 0
    }
}

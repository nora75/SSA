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
                Toast.makeText(this, "Dbに確認させに行きたい", Toast.LENGTH_LONG).show()
                //dbにアクセスするためのコードを書く
                if (checknum != CompareAddress(GetMailAddress())) {
                    Toast.makeText(this, "アドレスが一致しません", Toast.LENGTH_LONG).show()
                }
                if (checknum != CompareNumber(GetPassWord())) {
                    Toast.makeText(this, "パスワードが一致しません", Toast.LENGTH_LONG).show()
                } else {
                    val dataStore: SharedPreferences = getSharedPreferences("Confirm_Login", Context.MODE_PRIVATE)
                    val editor = dataStore.edit()
                    editor.putString("Address",GetMailAddress())
                    editor.putString("Pass",GetPassWord())
                    editor.commit()
                    Toast.makeText(this, "Dbに確認させに行きたい２", Toast.LENGTH_LONG).show()

                }
                Toast.makeText(this, "外", Toast.LENGTH_LONG).show()

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

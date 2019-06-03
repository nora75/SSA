package com.example.ssa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

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
            else{
                Toast.makeText(this, "Dbに登録させに行きたい", Toast.LENGTH_LONG).show()
                //dbにアクセスするためのコードを書く
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
}

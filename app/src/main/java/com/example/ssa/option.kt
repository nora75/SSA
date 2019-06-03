package com.example.ssa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_option.*

class option : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        account_info_button.setOnClickListener{
            var info_intent = Intent(this,account_info::class.java)
            startActivity(info_intent)
        }
        make_group.setOnClickListener{
            var makegroup_intent = Intent(this,add_group::class.java)
            startActivity(makegroup_intent)
            //↓のやつは新規登録画面に飛ぶためにテスト時に作成しました
            //var test = Intent(this,login::class.java)
            //startActivity(test)
        }
    }
}

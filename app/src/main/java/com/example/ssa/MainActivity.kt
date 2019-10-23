package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //書く画面への画面遷移用のリスナ―設定
        Write_Button.setOnClickListener {
            val write_intent = Intent(this, write::class.java)
            startActivity(write_intent)
        }

        //オプション画面への画面遷移用のリスナー設定
        Option_Button.setOnClickListener{
            val option_intet = Intent(this,option::class.java)
            startActivity(option_intet)
        }

        //話す画面への画面遷移用のリスナー設定
        Speak_Button.setOnClickListener{
            val talk_intet = Intent(this,Talk::class.java)
            startActivity(talk_intet)
        }

        //聞く画面への画面遷移用のリスナー設定
        Listen_See_Button.setOnClickListener{
            val listen_intet = Intent(this,Look::class.java)
            startActivity(listen_intet)
        }
    }

    override fun onResume() {
        super.onResume()

        val dataStore: SharedPreferences = getSharedPreferences("Confirm_Login", Context.MODE_PRIVATE)
        val pass = dataStore.getString("Pass","")
        if(pass.equals("")){
            val intent = Intent(this,login::class.java)
            startActivity(intent)
        }
    }

}


package com.example.ssa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //書く画面への画面遷移用のリスナ―設定
        Write_Button.setOnClickListener {
            val writeIntent = Intent(this, write::class.java)
            startActivity(writeIntent)
        }

        //オプション画面への画面遷移用のリスナー設定
        Option_Button.setOnClickListener{
            val optionIntent = Intent(this,option::class.java)
            startActivity(optionIntent)
        }

        //話す画面への画面遷移用のリスナー設定
        Speak_Button.setOnClickListener{
            val talkIntent = Intent(this,Talk::class.java)
            startActivity(talkIntent)
        }

        //聞く画面への画面遷移用のリスナー設定
        Listen_See_Button.setOnClickListener{
            val lookIntent = Intent(this,Look::class.java)
            startActivity(lookIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AlertDialog.Builder(this)
            .setTitle("家族トーク")
            .setMessage("グループを組んだ家族と日記や音声を共有するアプリケーションです。")
            .show()

        return true
    }

    override fun onResume() {
        super.onResume()
        val dataStore: SharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val pass = dataStore.getString("Pass","")
            if(pass.equals("")){
                val intent = Intent(this,login::class.java)
                startActivity(intent)
            }
    }
}


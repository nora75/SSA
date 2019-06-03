package com.example.ssa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

<<<<<<< HEAD
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

        //オプション画面への画面遷移用のリスナー設定
        Speak_Button.setOnClickListener{
            val talk_intet = Intent(this,talk::class.java)
            startActivity(talk_intet)
        }

        //オプション画面への画面遷移用のリスナー設定
        Listen_See_Button.setOnClickListener{
            val listen_intet = Intent(this,listen::class.java)
            startActivity(listen_intet)
        }

=======
        button1.setOnClickListener{
            val intent = Intent(this,Talk::class.java)
            startActivity(intent)
        }
>>>>>>> master
    }

}


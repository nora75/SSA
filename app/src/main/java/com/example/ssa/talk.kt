package com.example.ssa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_talk.*

class talk : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)

        to_listen.setOnClickListener {
            val listen = Intent(this,listen::class.java)
//            setContentView(R.layout.activity_listen)
            startActivity(listen)
        }

        to_write.setOnClickListener {
            setContentView(R.layout.activity_write)
        }

        talk_button.setOnClickListener {
            toggleTalk()
            Toast.makeText(this, "テストメッセージです", Toast.LENGTH_SHORT).show()
            alert_talk.setVisibility(View.VISIBLE)
        }

    }

    private fun toggleTalk() {
        talk_button.setBackgroundColor(244000000)
    }
}

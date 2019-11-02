package com.example.ssa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SeeDainay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_dainay)
        val intent = getIntent()
        val taketitle = intent.getStringExtra("title")
        val takeContent = intent.getStringExtra("Data")

        var mail_address = findViewById(R.id.Seetitle) as TextView
        mail_address.setText(taketitle)

        var content = findViewById(R.id.See_Memo_Content) as TextView
        content.setText(takeContent)

    }
}

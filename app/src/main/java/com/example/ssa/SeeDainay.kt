package com.example.ssa

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class SeeDainay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_dainay)
        val intent = getIntent()
        val taketitle = intent.getStringExtra("title")
        val takeContent = intent.getStringExtra("Data")
        val takeImage = intent.getByteArrayExtra("Image")
        val bitmap = BitmapFactory.decodeByteArray(takeImage, 0,takeImage.size)
        /*
        *取得したデータを表示する
         */
        val mail_address = findViewById(R.id.Seetitle) as TextView
        mail_address.setText(taketitle)

        val content = findViewById(R.id.See_Memo_Content) as TextView
        content.setText(takeContent)

        val image = findViewById(R.id.See_imagePreview) as ImageView
        image.setImageBitmap(bitmap)
    }
}

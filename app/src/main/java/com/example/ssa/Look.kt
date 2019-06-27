package com.example.ssa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import java.io.BufferedReader
import java.io.File

class Look : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look)

        val view = findViewById(R.id.imageView) as ImageView

    }
    private fun readFiles(file: String): String? {

        // to check whether file exists or not
        val readFile = File(applicationContext.filesDir, file)

        if(!readFile.exists()){
            Log.d("debug","No file exists")
            return null
        }
        else{
            return readFile.bufferedReader().use(BufferedReader::readText)
        }
    }
}

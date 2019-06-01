package com.example.ssa

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_write.*

class write : AppCompatActivity() {

    companion object {
        private val CAMERA_REQUEST_CODE = 1
        private val  CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
    }

    override fun onResume() {
        super.onResume()

        camera_button.setOnClickListener {
            // カメラ機能を実装したアプリが存在するかチェック
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
                if (checkCameraPermission()) {
                    takePicture()
                } else {
                    grantCameraPermission()
                }
            } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                data?.extras?.get("data")?.let {
                imageView.setImageBitmap(it as Bitmap)
            }
        }
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)


    private fun grantCameraPermission() =
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE)


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            }
        }
    }
}

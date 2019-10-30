package com.example.ssa

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.httpUpload
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_write.*
import java.io.ByteArrayOutputStream
import java.io.File

class write : AppCompatActivity() {

    companion object {
        private val CAMERA_REQUEST_CODE = 1
        private val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        hozon_button.setOnClickListener(View.OnClickListener {
            var content_id = findViewById(R.id.Memo_Content) as EditText
            var title_id = findViewById(R.id.title) as EditText
            var iamge_id = findViewById(R.id.imageView) as ImageView
            iamge_id.isEnabled

            var a = imageView.resources

            //エディットテキストからテキストを得る
            val contents = content_id.text.toString()
            val title = title_id.text.toString()
            val group_id = 1
            //val image = (imageView.drawable as BitmapDrawable).bitmap

            if (!contents.isEmpty() && !title.isEmpty() && !iamge_id.isEnabled) {
                val Contents = saveFile(GetFile().getWrite(filesDir), contents)
                val image = saveImage((imageView.drawable as BitmapDrawable).bitmap,GetFile().getPict(filesDir),this)
                val info = listOf(
                    "user_id" to "111",
                    "data_name" to "${Contents.name}",
                    "data_type" to "1",
                    "title" to "$title",
                    "image_name" to "$image.name")
                val header : HashMap<String, String> = hashMapOf("Content-Type" to "multipart/form-data")

                //"http://34.83.80.2:8000/group/$group_id"
                    Fuel.upload("http://34.83.80.2:8000/group/$group_id",parameters = info)
                    .add((FileDataPart(File(Contents.path),name = "Data")))
                    .add(FileDataPart(File(image?.path),name = "Image"))
                    .response{result ->
                        when(result){
                            is Result.Failure -> {
                                Toast.makeText(this,"失敗しました",Toast.LENGTH_LONG).show()
                                val ex = result.getException()
                                Log.d("error msg","${ex.toString()}")
                            }
                            is Result.Success -> {
                                val ex = result.get()
                                Toast.makeText(this,"成功しました",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
            } else {
                Toast.makeText(this, "タイトルと内容を入力してください。：保存に失敗しました", Toast.LENGTH_LONG).show()
            }
        })
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
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                takePicture()
            }
        }
    }

    //テキストファイルの保存
    private fun saveFile(filename: String, content: String): File {
        val FILENAME = filename
        // ファイルの書き込み
        val writeFile = File(FILENAME)
        writeFile.writeText(content)
        return writeFile
    }

    //画像ファイルの保存
    private fun saveImage(bmp: Bitmap, outputFileName: String, context: Context) : File? {
        try{
            val FILENAME = outputFileName
            val byteArrayOutputStream = ByteArrayOutputStream()
            val SaveImage = File(FILENAME)

            bmp!!.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream)
            SaveImage.writeBytes(byteArrayOutputStream.toByteArray())
            return SaveImage
        }
        catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
}
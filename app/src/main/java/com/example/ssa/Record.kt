package com.example.ssa

import android.media.MediaRecorder
import java.io.File

/*
* 録音クラス、録音、その停止をするクラス。
 */
class Record {

    // インスタンスの作成
    private val mr = MediaRecorder()
    private val gf = GetFile()

    /*
    * 録音を開始する。
     */
    fun startRecording(filesDir: File) {
        newRecord(filesDir)
        mr.prepare()
        mr.start()
    }

    /*
    * 録音を停止する。
     */
    fun stopRecording() {
        mr.stop()
        mr.reset()
    }

    /*
    * 新規に録音するファイルの設定、形式や、パスを設定する。
     */
    private fun newRecord(filesDir: File) {
        mr.setAudioSource(MediaRecorder.AudioSource.MIC) // 録音デバイスの指定 (マイク)
        mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // 保存形式の指定 ()
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT) // エンコーダの指定 (デフォルトのもの)
        mr.setOutputFile(gf.getTalk(filesDir))
    }
}

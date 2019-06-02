package com.example.ssa

import java.io.File

class GetFile {
    private val name = GetName()
    private var talkDirCheck: Boolean = false
    private var writeDirCheck: Boolean = false

    /*
    * 話す(talk)画面用
    * GetNameにより、名前を取得し、このアプリの内部の保存先のパスを取得後、Fileオブジェクトを元に、相対パスを返す。
    */
    fun getTalk(filesDir: File): String {
        val fl = File("$filesDir/Record", "Record" + name.get())
        if (!talkDirCheck) {
            talkDirCheck = checkDir(fl)
        }
        return fl.absolutePath
    }

    /*
    * 書く(write)画面用
    * GetNameにより、名前を取得し、このアプリの内部の保存先のパスを取得後、Fileオブジェクトを元に、相対パスを返す。
    */
    fun getWrite(filesDir: File): String {
        val fl = File("$filesDir/Write", "Write" + name.get())
        if (!writeDirCheck) {
            writeDirCheck = checkDir(fl)
        }
        return fl.absolutePath
    }

    /*
    * 書き込む親dirを確認して、無ければ作成する。
    */
    private fun checkDir(fl: File): Boolean {
        if (!fl.parentFile.exists()) {
            fl.parentFile.mkdir()
        }
        return true
    }

}
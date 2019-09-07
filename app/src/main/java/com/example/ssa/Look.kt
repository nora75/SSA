package com.example.ssa

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpUpload
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_look.*
import java.io.BufferedReader
import java.io.File
import java.util.*
import android.widget.ListAdapter as ListAdapter1
import com.example.ssa.ViewHolder as ViewHolder1
import com.example.ssa.ViewHolder as ViewHolder2

class Look : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look)
        val items = Array(20,{i -> "Title-$i"})
        val names = listOf(
            "サンプルA",
            "サンプルB",
            "サンプルC",
            "サンプルD",
            "サンプルE"
        )
        val date = listOf(
            "9/2",
            "9/3",
            "9/4",
            "9/5",
            "9/6"
        )
        val title = listOf(
            "孫とあそんだよ",
            "富士山に行ってきました",
            "温泉旅行にいきました",
            "お花見に行きました",
            "家で寝てました"
        )
        val look = List(names.size){i ->SampleData(names[i],date[i],title[i])}

        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items)
        myListView.adapter = adapter
    }
}


//
/*class ListAdapter(context:Context,look: List<SampleData>) : ArrayAdapter<SampleData>(context,0,look){
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder: com.example.ssa.ViewHolder

        if(view == null){
            view = layoutInflater.inflate(R.layout.my_text_view, parent,false)
            holder = com.example.ssa.ViewHolder(

            )
            view.tag = holder
        }else{
            holder = view.tag as com.example.ssa.ViewHolder
        }

        var sample = getItem(position) as SampleData
        holder.nameTextView.text = sample.names
        holder.dateTextView.text = sample.date
        holder.titleTextView.text = sample.title

        return view!!
    }

}
*/
package com.example.ssa

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.my_text_view.view.*
import android.widget.ListAdapter as ListAdapter1
import com.example.ssa.ViewHolder as ViewHolder1
import com.example.ssa.ViewHolder as ViewHolder2

class Look : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look)

        val names = listOf(
            "おじいちゃん",
            "おばあちゃん",
            "ははおや",
            "ちちおや",
            "まご"
        )

        val title = listOf(
            "おはなみ",
            "りょこう",
            "しごと",
            "やすみ",
            "しゅうがくりょこう"
        )

        val date = listOf(
            "9/2",
            "9/3",
            "9/4",
            "9/5",
            "9/6"
        )

        val test = List(names.size){i ->ProttypeData(names[i],title[i],date[i])}
        val adapter = SampleListAdapter(this,test)
        myListView.adapter = adapter

        myListView.setOnItemClickListener{adapterView,view,postion,id ->
            val name = view.findViewById<TextView>(R.id.text1).text
            Toast.makeText(this,"$name",Toast.LENGTH_LONG).show()
        }
    }
}

data class ProttypeData(
    val names:String,
    val title:String,
    val date: String
)

data class SampleViewHolder(
    val text1: TextView,
    val text2: TextView,
    val text3: TextView
)

class SampleListAdapter(context: Context,sample:List<ProttypeData>) : ArrayAdapter<ProttypeData>(context,0,sample){
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder : SampleViewHolder

        if(view == null){
            view = layoutInflater.inflate(R.layout.my_text_view, parent ,false)
            holder = SampleViewHolder(
                view.text1,
                view.text2,
                view.text3
            )
            view.tag = holder
        }
        else
        {
            holder = view.tag as SampleViewHolder
        }
        var sample = getItem(position) as ProttypeData
        holder.text1.text = sample.names
        holder.text2.text = sample.title
        holder.text3.text = sample.date

        return view
    }
}
package com.bibleapp.faithdaily.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.model.ImageModel
import com.bibleapp.faithdaily.R
import java.util.*

class ImageAdapter(ctx: Context?, imageModelArrayList: ArrayList<ImageModel>) :
    RecyclerView.Adapter<ImageAdapter.MyViewHolder?>() {
    private val inflater: LayoutInflater
    private val imageModelArrayList: ArrayList<ImageModel>


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.iv.setImageResource(imageModelArrayList[position].image_drawable)
        holder.time.setText(imageModelArrayList[position].name)
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var time: TextView
        var iv: ImageView

        init {
            time = itemView.findViewById<View>(R.id.tv) as TextView
            iv = itemView.findViewById<View>(R.id.iv) as ImageView
        }
    }

    init {
        inflater = LayoutInflater.from(ctx)
        this.imageModelArrayList = imageModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }
}

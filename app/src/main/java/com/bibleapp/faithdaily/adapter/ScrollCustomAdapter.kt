package com.bibleapp.faithdaily.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.model.ImageModel

abstract class ScrollCustomAdapter(
    private val imageModelArrayList: ArrayList<ImageModel>,
    private val clickListener: (data: ImageModel) -> Unit
) : RecyclerView.Adapter<ScrollCustomAdapter.MyViewHolder>() {

    abstract fun load()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScrollCustomAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ScrollCustomAdapter.MyViewHolder, position: Int) {
        holder.bind(imageModelArrayList[position])
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class MyViewHolder(
        itemView: View,
        private val clickListener: (data: ImageModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var imageModel: ImageModel? = null

        private var time: TextView = itemView.findViewById(R.id.tv) as TextView
        private var iv: ImageView = itemView.findViewById(R.id.iv) as ImageView

        init {
            itemView.setOnClickListener {
                imageModel?.let { clickListener.invoke(it) }
            }
        }

        fun bind(imageModel: ImageModel) {
            this.imageModel = imageModel
            iv.setImageResource(imageModel.image_drawable)
            time.text = imageModel.name
        }
    }
}

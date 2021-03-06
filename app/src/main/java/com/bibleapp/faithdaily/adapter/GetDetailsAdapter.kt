package com.bibleapp.faithdaily.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.model.FaithDailyResponse
import kotlinx.android.synthetic.main.item_preview.view.*

class GetDetailsAdapter (private var listOfPosts: List<FaithDailyResponse>):
    RecyclerView.Adapter<GetDetailsAdapter.GetDetailsViewHolder>() {

    private val limit = 10


    inner class GetDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind (faithdaily: FaithDailyResponse){
            itemView.apply {
                /* tvDate.text = faithdaily.date
                */ tvTitle.text = faithdaily.title
                tvDescription.text = faithdaily.daily_message
                tvVerse.text = faithdaily.bible_verse
                setOnClickListener {
                    onItemClickListener?.let { it(faithdaily) }
                }

            }
        }
    }

    private var onItemClickListener: ((FaithDailyResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (FaithDailyResponse) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetDetailsViewHolder {
        return GetDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_details,
                parent,
                false
            )
        ) }

    override fun onBindViewHolder(holder: GetDetailsViewHolder, position: Int) {
        val faithdaily = listOfPosts[position]
        holder.bind(faithdaily)
    }

    override fun getItemCount(): Int {
        if(listOfPosts.size > limit){
            return limit
        }
        else{
            return listOfPosts.size
        }

    }
}




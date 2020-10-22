package com.bibleapp.faithdaily.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.FaithDailyResponse
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.ui.HomeFragment
import kotlinx.android.synthetic.main.item_preview.view.*





class FaithDailyAdapter (private var listOfPosts: List<FaithDailyResponse>): RecyclerView.Adapter<FaithDailyAdapter.FaithDailyViewHolder>() {

    inner class FaithDailyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind (faithdaily:FaithDailyResponse){
            itemView.apply {
                tvSource.text = faithdaily.date
                tvTitle.text = faithdaily.title
                tvDescription.text = faithdaily.daily_message
                tvPublishedAt.text = faithdaily.bible_verse
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaithDailyViewHolder {
        return FaithDailyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_preview,
                parent,
                false
            )
        ) }

    override fun onBindViewHolder(holder: FaithDailyViewHolder, position: Int) {
        val faithdaily = listOfPosts[position]
        holder.bind(faithdaily)
    }

    override fun getItemCount(): Int {
        return listOfPosts.size
    }
}




package com.bibleapp.faithdaily.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.model.FaithDailyResponse
import kotlinx.android.synthetic.main.item_preview.view.*

class FavoriteAdapter(private var listOfPosts: MutableList<FaithDailyResponse>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(faithdaily: FaithDailyResponse) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val faithdaily = listOfPosts[position]
        holder.bind(faithdaily)
    }


    fun removeAt(position: Int) {
        listOfPosts.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun getItemCount(): Int {
        return listOfPosts.size
    }
}




package com.bibleapp.faithdaily.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.FaithDailyResponse
import com.bibleapp.faithdaily.R
import kotlinx.android.synthetic.main.item_preview.view.*

class FaithDailyAdapter : RecyclerView.Adapter<FaithDailyAdapter.FaithDailyViewHolder>() {

    inner class FaithDailyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<FaithDailyResponse>() {
        override fun areItemsTheSame(oldItem: FaithDailyResponse, newItem: FaithDailyResponse): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: FaithDailyResponse, newItem: FaithDailyResponse): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaithDailyViewHolder {
        return FaithDailyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FaithDailyViewHolder, position: Int) {
        val faithdaily = differ.currentList[position]
        holder.itemView.apply {
            tvSource.text = faithdaily.date
            tvTitle.text = faithdaily.title
            tvDescription.text = faithdaily.daily_message
            tvPublishedAt.text = faithdaily.bible_verse
            setOnClickListener {
                onItemClickListener?.let { it(faithdaily) }
            }
        }
    }

    private var onItemClickListener: ((FaithDailyResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (FaithDailyResponse) -> Unit) {
        onItemClickListener = listener
    }
}



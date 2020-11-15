package com.bibleapp.faithdaily.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.bibleapp.faithdaily.MainActivity
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.adapter.FavoriteAdapter
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.util.SwipeToDeleteCallBack
import com.bibleapp.faithdaily.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_preview.*


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    lateinit var viewModel: MainViewModel
    lateinit var postAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel


        viewModel.getSavFav().observe(viewLifecycleOwner, Observer { articles ->
            setupRecyclerView(articles)

            if (postAdapter!!.itemCount == 0) {
                txtEmptyFav.visibility = View.VISIBLE
            }


            postAdapter.setOnItemClickListener {
                val intent = Intent(context, FavoriteDetailsActivity::class.java)
                intent.putExtra("KeyFav", it.daily_message)
                intent.putExtra("KeyTitleFav", it.title)
                intent.putExtra("KeyVerseFav", it.bible_verse)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity as MainActivity,
                    (tvDescription as View?)!!, "profile"
                )
                startActivity(intent, options.toBundle())


            }

            postAdapter.setLongListener {

                showDialog(it, it.daily_message)

            }


        })


    }


    private fun delete(faithdaily: FaithDailyResponse) {
        val swipeHandler = object : SwipeToDeleteCallBack(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = favRecycler.adapter as FavoriteAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                viewModel.delete(faithdaily)

            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(favRecycler)

    }

    private fun showDialog(faithdaily: FaithDailyResponse, desc: String) {
        val dialog = Dialog(context!!)
        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_layout)
        val delete = dialog.findViewById(R.id.delete) as TextView
        val share = dialog.findViewById(R.id.share) as TextView
        delete.setOnClickListener {
            viewModel.delete(faithdaily)
            dialog.dismiss()
        }
        share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, desc)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))

            dialog.dismiss()
        }
        dialog.show()

    }

    private fun setupRecyclerView(
        response: MutableList<FaithDailyResponse>
    ) {
        postAdapter = FavoriteAdapter(response)

        favRecycler.addItemDecoration(
            DividerItemDecoration(
                favRecycler.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )




        favRecycler.apply {

            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)

        }
        postAdapter.notifyDataSetChanged()
    }


    interface ClickListener {
        fun onClick(view: View?, position: Int)
        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(
        context: Context?,
        recycleView: RecyclerView,
        clicklistener: ClickListener?
    ) :
        OnItemTouchListener {
        private val clicklistener: ClickListener?
        private val gestureDetector: GestureDetector
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

        init {
            this.clicklistener = clicklistener
            gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recycleView.findChildViewUnder(e.x, e.y)
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child))
                    }
                }
            })
        }
    }

}
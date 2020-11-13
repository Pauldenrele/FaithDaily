package com.bibleapp.faithdaily.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.MainActivity
import com.bibleapp.faithdaily.viewmodel.MainViewModel
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.util.SwipeToDeleteCallBack
import com.bibleapp.faithdaily.adapter.FavoriteAdapter
import com.bibleapp.faithdaily.model.FaithDailyResponse
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    lateinit var viewModel: MainViewModel
    lateinit var postAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        viewModel.getSavFav().observe(viewLifecycleOwner, Observer { articles ->
            setupRecyclerView(articles)

            postAdapter.setOnItemClickListener {
                delete(it)

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
            //   postAdapter.setDeleteClickListener(this@FavoriteFragment)


            //  addOnScrollListener(this.scrollListener)

        }
        postAdapter.notifyDataSetChanged()
    }

    /* override fun onDeleteClicked(faithdaily: FaithDailyResponse) {
        delete(faithdaily)

     }
*/

}
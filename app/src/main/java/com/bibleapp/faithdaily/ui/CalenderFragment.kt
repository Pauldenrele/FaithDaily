package com.bibleapp.faithdaily.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.VerticalAdapterJava
import kotlinx.android.synthetic.main.fragment_calender.*
import java.util.*

class CalenderFragment : Fragment(R.layout.fragment_calender),
    VerticalAdapterJava.DateSelectionListener {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        custom_calender_view.updateCalendar(6)
        custom_calender_view.setDateSelectionColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorAccent
            )
        )
      //   custom_calender_view.setListener(activity)

    }


    override fun onDateSelected(dateList: List<Date>?) {
        for (date in dateList!!) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            //Log.d(MainActivity.TAG, "Main Activity date list" + calendar[Calendar.DATE])

        }
    }
}
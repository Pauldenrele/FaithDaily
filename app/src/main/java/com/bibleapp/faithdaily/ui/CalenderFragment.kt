package com.bibleapp.faithdaily.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bibleapp.faithdaily.CustomVerticalCalenderView
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.VerticalAdapter
import com.bibleapp.faithdaily.VerticalAdapterJava
import kotlinx.android.synthetic.main.fragment_calender.*
import java.util.*

class CalenderFragment : Fragment(R.layout.fragment_calender), VerticalAdapterJava.DateSelectionListener{



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


      /*  val customVerticalCalenderView =
        (R.id.custom_calender_view) as CustomVerticalCalenderView
      */  //customVerticalCalenderView.setFromYear(2017);
        //customVerticalCalenderView.setToYear(2017);
        //customVerticalCalenderView.setFromYear(2017);
        //customVerticalCalenderView.setToYear(2017);
        custom_calender_view.updateCalendar(6)
        /* Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.DATE, -5);
        customVerticalCalenderView.setRangeByDefault(startCal.getTime(), Calendar.getInstance().getTime());
        customVerticalCalenderView.updateCalendar();
        CustomVerticalCalenderView.setRange(true);*/
        //CustomVerticalCalenderView.setSingleClick();
        /* Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.DATE, -5);
        customVerticalCalenderView.setRangeByDefault(startCal.getTime(), Calendar.getInstance().getTime());
        customVerticalCalenderView.updateCalendar();
        CustomVerticalCalenderView.setRange(true);*/
        //CustomVerticalCalenderView.setSingleClick();
       custom_calender_view.setDateSelectionColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorAccent
            )
        )
       // custom_calender_view.setListener(context)

    }




    override fun onDateSelected(dateList: List<Date>?) {
        for (date in dateList!!) {
            val calendar = Calendar.getInstance()
            calendar.time = date
        }
    }
}
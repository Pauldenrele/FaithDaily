package com.bibleapp.faithdaily.ui

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.ReminderBroadCast
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sharedPrefs: SharedPreferences = context!!.getSharedPreferences(
            "switchstate",
            MODE_PRIVATE
        )
        switch_button.isChecked = sharedPrefs.getBoolean("saveSwitchState", false)

        switchButton()

    }

    fun switchButton() {
        switch_button.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                createNotificationChannel()

                Toast.makeText(context, "Item Two Clicked", Toast.LENGTH_LONG).show()
                val editor: SharedPreferences.Editor =
                    context!!.getSharedPreferences("switchstate", MODE_PRIVATE).edit()
                editor.putBoolean("saveSwitchState", true)
                editor.apply()


                val intent1 = Intent(context, ReminderBroadCast::class.java)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0)


                val alarmManager =
                    context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val timeAtBtnClick = System.currentTimeMillis()

                val timeSecinMillis = 1000 * 60 *60 * 10.toLong()

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    timeSecinMillis,
                    pendingIntent
                )
                /*   alarmManager[AlarmManager.RTC_WAKEUP,  timeSecinMillis] =
                       pendingIntent
   */
            } else if (!isChecked) {
                val editor: SharedPreferences.Editor =
                    context!!.getSharedPreferences("switchstate", MODE_PRIVATE).edit()
                editor.putBoolean("saveSwitchState", false)
                editor.apply()

             //   Toast.makeText(context , "OFFFF" , Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "MyReminderChannel"
            val description = "My channel for faithDaily"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("NotifyMe", name, importance)
            channel.description = description
            val notificationManager: NotificationManager =
                context!!.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}
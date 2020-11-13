package com.bibleapp.faithdaily;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.bibleapp.faithdaily.ui.SettingsFragment;

import static android.content.Context.ALARM_SERVICE;

public class ReminderBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

      // intent = new Intent(context, SettingsFragment.class);
     //   intent.putExtra("yourpackage.notifyId", NOTIFICATION_ID);

         intent = new Intent(context, MainActivity.class);
        //intent.putExtra("yourpackage.notifyId", NOTIFICATION_ID);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , "NotifyMe")
                .setSmallIcon(R.drawable.fdicon)
                .setContentTitle("Todays Verse")
                .setContentIntent(pIntent)
                .setContentText("A soft reminder")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200 , builder.build());

    }


}

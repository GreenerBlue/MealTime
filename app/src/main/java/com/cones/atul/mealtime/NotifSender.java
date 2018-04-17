package com.cones.atul.mealtime;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class NotifSender extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"Pagadar");
        builder.setAutoCancel(true).setContentText("Collect from counter").setContentTitle("Your order is ready!")
                .setSmallIcon(R.drawable.ic_launcher_background).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE);

        Log.i("TIM","Cheers!");

        PendingIntent pIt = PendingIntent.getActivity(context,0,new Intent(),0);
        builder.setContentIntent(pIt);

        NotificationManagerCompat mg = NotificationManagerCompat.from(context);
        mg.notify(1,builder.build());

    }
}

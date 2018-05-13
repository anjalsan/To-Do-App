package todolist.task.com.todolist.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import todolist.task.com.todolist.R;
import todolist.task.com.todolist.view.activity.MainActivity;

public class LocalNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String notification_title = "TODO";
        String notification_description = "You have a reminder";
        int notification_id = (int) (System.currentTimeMillis() & 0xfffffff);

        if (intent.getStringExtra("Title") != null){
            notification_title = intent.getStringExtra("Title");
        }

        if (intent.getStringExtra("Description") != null){
            notification_description = intent.getStringExtra("Description");
        }

        notification_id = intent.getIntExtra("Id", notification_id);

        showNotification(context, notification_title, notification_description, notification_id);
    }

    public static void showNotification(Context context, String title, String content, int notification_id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel-01";
        String channelName = "TODO";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setVibrate(new long[]{100});

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                notification_id,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        if (notificationManager != null) {
            notificationManager.notify(notification_id, mBuilder.build());
        }
    }
}

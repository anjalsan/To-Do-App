package todolist.task.com.todolist.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import todolist.task.com.todolist.model.Todo;

import static android.content.Context.ALARM_SERVICE;

public class TodoNotificationManager {

    public static void createNotificationForTodo(Context context, Todo todo) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(context, LocalNotification.class);
        intent.putExtra("Title", todo.getTitle());
        intent.putExtra("Id", todo.getId());
        if (todo.getDescription() != null && todo.getDescription().length() != 0){
            intent.putExtra("Description", todo.getDescription());
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, todo.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null && todo.getTodo_schedule_at() > System.currentTimeMillis()) {
            long time = (long) todo.getTodo_schedule_at();
            alarmManager.set(AlarmManager.RTC_WAKEUP, time,pendingIntent);
        }
    }

    public static void cancelNotificationForTodo(Context context, Todo todo) {
        Intent intent = new Intent(context, LocalNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                todo.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (am != null) {
            am.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}

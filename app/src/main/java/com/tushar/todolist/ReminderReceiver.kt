package com.tushar.todolist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val taskName = intent?.getStringExtra("taskName") ?: "Task"

        // Create the notification channel (for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "taskReminderChannel"
            val channelName = "Task Reminder"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Channel for task reminders"
                setSound(null, null)
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create an intent to launch the AlarmActivity (full-screen activity)
        val openAppIntent = Intent(context, AlarmActivity::class.java)
        openAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Show Notification with an alarm sound
        val alarmSound = android.net.Uri.parse("android.resource://${context.packageName}/raw/alarm_sound")
        val notificationBuilder = NotificationCompat.Builder(context, "taskReminderChannel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Task Reminder")
            .setContentText("Don't forget: $taskName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(alarmSound)
            .setAutoCancel(true)

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            notify(1001, notificationBuilder.build())  // Ensure the ID is unique for each notification
        }
        // Trigger the full-screen AlarmActivity (important)
        val fullScreenIntent = Intent(context, AlarmActivity::class.java)
        fullScreenIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        fullScreenIntent.putExtra("taskName", taskName)
        context.startActivity(fullScreenIntent)  // Explicitly start the activity here

    }
}

package com.tushar.todolist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val taskName = intent?.getStringExtra("taskName") ?: "Task"
        Log.d("ReminderReceiver", "Reminder triggered for task: $taskName")

        // Create the notification channel (for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "taskReminderChannel"
            val channelName = "Task Reminder"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Channel for task reminders"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create an intent to launch the app when the notification is clicked
        val openAppIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, "taskReminderChannel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)  // Add your notification icon here
            .setContentTitle("Task Reminder")
            .setContentText("Don't forget: $taskName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)  // Dismiss the notification after the user clicks on it

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            notify(1001, notificationBuilder.build())  // Ensure the ID is unique for each notification
        }
    }
}
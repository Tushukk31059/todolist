package com.tushar.todolist

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast

class AlarmActivity : AppCompatActivity() {
    private lateinit var ringtone: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make the activity full-screen and show over lock screen
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        setContentView(R.layout.activity_alarm)

        // Play the alarm sound
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone.play()

        // Stop the alarm when user clicks "Dismiss"
        val dismissButton: Button = findViewById(R.id.dismissButton)
        dismissButton.setOnClickListener {
            ringtone.stop()
            finish()  // Close the activity
        }

        // Snooze button functionality (optional)
        val snoozeButton: Button = findViewById(R.id.snoozeButton)
        snoozeButton.setOnClickListener {
            // Implement snooze functionality (e.g., reschedule the alarm)

            ringtone.stop()

            // Reschedule the alarm for 2 minutes later
            val snoozeTimeInMillis = 2 * 60 * 1000L  // 2 minutes in milliseconds

            val snoozeIntent = Intent(this, ReminderReceiver::class.java).apply {
                putExtra("taskName", intent.getStringExtra("taskName"))  // Pass task name for snooze notification
            }

            val snoozePendingIntent = PendingIntent.getBroadcast(
                this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Schedule the snooze alarm using AlarmManager
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val snoozeTime = System.currentTimeMillis() + snoozeTimeInMillis
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, snoozeTime, snoozePendingIntent)
            Toast.makeText(this,"Alarm Snoozed For 2 Minutes",Toast.LENGTH_LONG).show()

            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ringtone.isPlaying) {
            ringtone.stop()
        }
    }

}

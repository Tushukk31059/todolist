package com.tushar.todolist

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button

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

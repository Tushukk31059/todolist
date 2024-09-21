package com.tushar.todolist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val taskName= intent?.getStringExtra("taskName")
        Toast.makeText(context,"Reminder:$taskName",Toast.LENGTH_LONG).show()
        }
}
package com.tushar.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlarmManager
import android.app.MediaRouteButton
import android.content.AbstractThreadedSyncAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker


class MainActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var taskInput: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var periodSpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timePicker=findViewById(R.id.timePicker)
        periodSpinner=findViewById(R.id.periodSpinner)
        addButton=findViewById(R.id.addButton)
        taskInput=findViewById(R.id.taskInput)

    }
}
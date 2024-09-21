package com.tushar.todolist
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var addButton: Button
    private lateinit var taskInput: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var periodSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }

        timePicker=findViewById(R.id.timePicker)
        periodSpinner=findViewById(R.id.periodSpinner)
        addButton=findViewById(R.id.addButton)
        taskInput=findViewById(R.id.taskInput)

        taskAdapter= TaskAdapter()
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter=taskAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        val periods= arrayOf("Daily","Weekly","Monthly","Yearly")
        val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,periods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        periodSpinner.adapter=adapter
        addButton.setOnClickListener{
            val taskText=taskInput.text.toString()
            if(taskText.isNotEmpty()){
                val hour = timePicker.hour
                val minute=timePicker.minute
                val period=periodSpinner.selectedItem.toString()
                val task= Task(taskText,hour,minute,period)
                taskAdapter.addTask(task)

                setReminder(task)
                taskInput.text.clear()

            }else{
                Toast.makeText(this,"Please Enter A Task",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setReminder(task: Task){
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java)
        intent.putExtra("taskName", task.name)
        val pendingIntent = PendingIntent.getBroadcast(this, task.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, task.hour)
            set(Calendar.MINUTE, task.minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)  // Move to the next day if the time has already passed

            }
           }
    }}

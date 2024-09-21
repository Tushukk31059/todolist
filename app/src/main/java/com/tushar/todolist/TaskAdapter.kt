package com.tushar.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    private val taskList= mutableListOf<Task>()

    class TaskViewHolder(view:View):RecyclerView.ViewHolder(view){
        val taskName:TextView=view.findViewById(R.id.taskName)
        val taskTime:TextView=view.findViewById(R.id.taskTime)
        val taskPeriod:TextView=view.findViewById(R.id.taskPeriod)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
        return TaskViewHolder(view)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task=taskList[position]
        val hour = if (task.hour == 0) 12 else if (task.hour > 12) task.hour - 12 else task.hour
        val minute = String.format("%02d", task.minute) // Ensures two digits for minute (e.g., 08:05)
        val period = if (task.hour >= 12) "PM" else "AM"

        holder.taskTime.text = String.format("%02d:%s %s", hour, minute, period)

        holder.taskName.text = task.name
        holder.taskPeriod.text = task.period
    }
    override fun getItemCount(): Int {
        return taskList.size
    }
    fun addTask(task: Task){
        taskList.add(task)
        notifyDataSetChanged()
    }
}
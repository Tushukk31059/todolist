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
        fun onCreateViewHolder(parent:ViewGroup,viewType:Int):TaskViewHolder{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
            return TaskViewHolder(view)
        }
        fun onBindViewHolder(holder: TaskViewHolder,position: Int){
            val task=taskList[position]
            holder.taskName.text=task.name
            holder.taskTime.text="$(task.hour):$(task.minute)"
            holder.taskPeriod.text=task.period
        }
        fun getItemCount():Int{
            return taskList.size
        }
        fun addTask(task: Task){
            taskList.add(task)
            notifyDataSetChanged()
        }
    }

}
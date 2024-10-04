package com.tushar.todolist

data class Task(
    val name:String,
    val hour:Int,
    val minute:Int,
    val period:String,
    val id:Int=(System.currentTimeMillis() % Int.MAX_VALUE).toInt()
)
package com.example.mvvm_search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    var id: Int = 0,
    var title: String,
    var description: String,
    var isCompleted: Boolean = false,
    var dueDate: String
)

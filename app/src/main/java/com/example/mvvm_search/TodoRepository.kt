package com.example.mvvm_search

import android.app.Application
import androidx.lifecycle.LiveData

class TodoRepository (application: Application) {

    private val todoDao: TodoDao
    private val allTodos: LiveData<List<Todo>>

    init {
        val database = TodoDatabase.getInstance(application)
        todoDao = database.todoDao()
        allTodos = todoDao.getAllTodos()
    }

    fun insert(todo: Todo) {
        TodoDatabase.databaseWriteExecutor.execute {
            todoDao.insert(todo)
        }
    }

    fun update(todo: Todo) {
        TodoDatabase.databaseWriteExecutor.execute {
            todoDao.update(todo)
        }
    }

    fun delete(todo: Todo) {
        TodoDatabase.databaseWriteExecutor.execute {
            todoDao.delete(todo)
        }
    }

    fun getAllTodos(): LiveData<List<Todo>> {
        return allTodos
    }
}

package com.example.mvvm_search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TodoViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository = TodoRepository(application)
    private val allTodos: LiveData<List<Todo>> = repository.getAllTodos()

    fun insert(todo: Todo) {
        repository.insert(todo)
    }

    fun update(todo: Todo) {
        repository.update(todo)
    }

    fun delete(todo: Todo) {
        repository.delete(todo)
    }

    fun getAllTodos(): LiveData<List<Todo>> {
        return allTodos
    }
}

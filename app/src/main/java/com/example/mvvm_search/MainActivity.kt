package com.example.mvvm_search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_search.EditTodoActivity.Companion.EXTRA_TODO_ID
import com.example.mvvm_search.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoViewModel: TodoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the RecyclerView
        todoAdapter = TodoAdapter(this)
        binding.recyclerView.adapter = todoAdapter

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

        // Initialize the ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        // Initialize the TodoViewModel
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        // Retrieve data from the database and display it in the RecyclerView
        todoViewModel.getAllTodos().observe(this, { todos ->
            todoAdapter.setTodos(todos)
            binding.progressBar.visibility = View.GONE
        })



        // Handle clicks on individual To-Do items
        todoAdapter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(todo: Todo) {
                val intent = Intent(applicationContext, EditTodoActivity::class.java)
                intent.putExtra(EXTRA_TODO_ID, todo.id)
                intent.putExtra(EXTRA_TODO_TITLE, todo.title)
                intent.putExtra(EXTRA_TODO_DESCRIPTION, todo.description)
                intent.putExtra(EXTRA_TODO_DUE_DATE, todo.dueDate)
                startActivityForResult(intent, EDIT_TODO_REQUEST)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_TODO_REQUEST -> {
                    val title = data?.getStringExtra(EXTRA_TODO_TITLE)
                    val description = data?.getStringExtra(EXTRA_TODO_DESCRIPTION)
                    val dueDate = data?.getStringExtra(EXTRA_TODO_DUE_DATE)
                    if (title != null && description != null && dueDate != null) {
                        val todo = Todo(
                            title = title,
                            description = description,
                            dueDate = dueDate
                        )
                        todoViewModel.insert(todo)
                        Toast.makeText(this, "To-Do item saved", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "To-Do item not saved", Toast.LENGTH_SHORT).show()
                    }
                }
                EDIT_TODO_REQUEST -> {
                    val id = data?.getIntExtra(EXTRA_TODO_ID, -1)
                    if (id == -1) {
                        Toast.makeText(this, "To-Do item can't be updated", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val title = data?.getStringExtra(EXTRA_TODO_TITLE)
                    val description = data?.getStringExtra(EXTRA_TODO_DESCRIPTION)
                    val dueDate = data?.getStringExtra(EXTRA_TODO_DUE_DATE)
                    if (title != null && description != null && dueDate != null) {
                        val todo = Todo(
                            id = id,
                            title = title,
                            description = description,
                            dueDate = dueDate
                        )
                        todoViewModel.update(todo)
                        Toast.makeText(this, "To-Do item updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "To-Do item not updated", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "To-Do item not saved or updated", Toast.LENGTH_SHORT).show()
        }
    }
}


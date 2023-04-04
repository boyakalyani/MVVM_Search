package com.example.mvvm_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_search.databinding.ActivityEditTodoBinding

class EditTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTodoBinding
    private val todoViewModel: TodoViewModel by viewModels()
    private var todoId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the todo id from the intent extras
        todoId = intent.getLongExtra(EXTRA_TODO_ID, -1L)

        // observe the todo by id
        todoViewModel.getTodoById(todoId).observe(this, { todo ->
            if (todo != null) {
                // update the UI with the todo data
                binding.editTextTitle.setText(todo.title)
                binding.editTextDescription.setText(todo.description)
                binding.editTextDueDate.setText(todo.dueDate)

                // set the priority spinner value
                when (todo.priority) {
                    Priority.HIGH -> binding.spinnerPriority.setSelection(0)
                    Priority.MEDIUM -> binding.spinnerPriority.setSelection(1)
                    Priority.LOW -> binding.spinnerPriority.setSelection(2)
                }

                // set the completed checkbox value
                binding.checkBoxCompleted.isChecked = todo.completed
            }
        })

        // set the onClickListener for the save button
        binding.buttonSave.setOnClickListener {
            // get the input data
            val title = binding.editTextTitle.text.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()
            val dueDate = binding.editTextDueDate.text.toString().trim()
            val priority = when (binding.spinnerPriority.selectedItemPosition) {
                0 -> Priority.HIGH
                1 -> RenderScript.Priority.MEDIUM
                else -> Priority.LOW
            }
            val completed = binding.checkBoxCompleted.isChecked

            // update the todo data
            val updatedTodo = Todo(
                id = todoId,
                title = title,
                description = description,
                dueDate = dueDate,
                priority = priority,
                completed = completed
            )
            todoViewModel.update(updatedTodo)

            // finish the activity
            finish()
        }
    }

    companion object {
        const val EXTRA_TODO_ID = "com.example.mvvm_search.EXTRA_TODO_ID"
        const val EXTRA_TODO_TITLE = "com.example.mvvm_search.EXTRA_TODO_TITLE"
        const val EXTRA_TODO_DESCRIPTION = "com.example.mvvm_search.EXTRA_TODO_DESCRIPTION"
        const val EXTRA_TODO_DUE_DATE = "com.example.mvvm_search.EXTRA_TODO_DUE_DATE"
    }

}

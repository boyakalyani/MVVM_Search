package com.example.mvvm_search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_search.databinding.ActivityAddTodoBinding

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var todoViewModel: TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get a reference to the TodoViewModel
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        // Set up the RecyclerView
        val adapter = TodoAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the LiveData returned by the getFilteredTodos method
//        todoViewModel.getFilteredTodos().observe(this, { todos ->
//            adapter.submitList(todos)
//        })
        todoViewModel.getFilteredTodos().observe(this, { todos ->
            adapter.submitList(todos)
        })

        // Set up the search bar
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Update the filter when the text in the search bar changes
                todoViewModel.filterTodos(newText)
                return true
            }
        })

    }
}
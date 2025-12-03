package com.example.todoapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.TodoAdapter
import com.example.todoapp.ui.TodoViewModel
import com.example.todoapp.ui.TodoViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory(
            TodoRepository(
                TodoDatabase.getDatabase(applicationContext).todoDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
        observeTodos()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onDeleteClick = { todo ->
                todoViewModel.deleteTodo(todo)
            },
            onCompletedChange = { todoId, isCompleted ->
                todoViewModel.updateTodoCompleted(todoId, isCompleted)
            }
        )

        binding.rvTodos.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun setupClickListeners() {
        binding.fabAddTodo.setOnClickListener {
            addTodo()
        }

        binding.etTodoInput.setOnEditorActionListener { _, _, _ ->
            addTodo()
            true
        }
    }

    private fun addTodo() {
        val todoText = binding.etTodoInput.text?.toString()?.trim()
        if (!todoText.isNullOrBlank()) {
            todoViewModel.insertTodo(todoText)
            binding.etTodoInput.text?.clear()
            binding.etTodoInput.clearFocus()
        }
    }

    private fun observeTodos() {
        lifecycleScope.launch {
            todoViewModel.todos.collect { todos ->
                todoAdapter.submitList(todos)
                updateEmptyState(todos.isEmpty())
            }
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.llEmptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.rvTodos.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}
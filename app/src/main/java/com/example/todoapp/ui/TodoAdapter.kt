package com.example.todoapp.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.Todo
import com.example.todoapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val onDeleteClick: (Todo) -> Unit,
    private val onCompletedChange: (Int, Boolean) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.apply {
                tvTodoTitle.text = todo.title

                // Clear previous listener to prevent unwanted triggers
                cbTodoComplete.setOnCheckedChangeListener(null)
                cbTodoComplete.isChecked = todo.isCompleted

                // Style text based on completion
                if (todo.isCompleted) {
                    tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvTodoTitle.alpha = 0.6f
                } else {
                    tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    tvTodoTitle.alpha = 1.0f
                }

                // Set listener after setting the checked state
                cbTodoComplete.setOnCheckedChangeListener { _, isChecked ->
                    onCompletedChange(todo.id, isChecked)
                }

                btnDeleteTodo.setOnClickListener {
                    onDeleteClick(todo)
                }
            }
        }
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}

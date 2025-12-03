package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepository

class TodoApplication : Application() {
    val database by lazy { TodoDatabase.getDatabase(this) }
    val repository by lazy { TodoRepository(database.todoDao()) }
}

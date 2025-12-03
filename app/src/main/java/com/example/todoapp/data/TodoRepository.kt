package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()

    suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)

    suspend fun deleteTodoById(todoId: Int) = todoDao.deleteTodoById(todoId)

    suspend fun updateTodoCompleted(todoId: Int, isCompleted: Boolean) =
        todoDao.updateTodoCompleted(todoId, isCompleted)
}

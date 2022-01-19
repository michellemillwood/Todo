package se.millwood.todo

import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    private val todos = mutableListOf<Todo>()

    fun createTodo(title: String) = addTodo(Todo(title))

    private fun addTodo(todo: Todo) = todos.add(0, todo)

    fun getTodos() = todos.toList()

    fun updateTodos(newTodos: List<Todo>) {
        todos.clear()
        todos.addAll(newTodos.sortedBy { it.isCompleted })
    }

}
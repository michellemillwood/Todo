package se.millwood.todo

import androidx.lifecycle.ViewModel
import java.util.*

class TodoViewModel : ViewModel() {

    private val todos = mutableListOf<Todo>()

    fun getTodos() = todos.sortedBy { it.isCompleted }

    fun createTodo(title: String) = addTodo(Todo(title))


    fun toggleCheckbox(todoId: UUID) {
        todos.find { it.id == todoId }?.apply { isCompleted = !isCompleted }
    }

    fun removeTodo(todoId: UUID) = todos.removeAll { it.id == todoId }

    private fun addTodo(todo: Todo) = todos.add(0, todo)
}

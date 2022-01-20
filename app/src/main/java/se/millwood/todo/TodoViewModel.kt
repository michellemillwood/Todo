package se.millwood.todo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class TodoViewModel : ViewModel() {

    private val _todos = MutableStateFlow(listOf<Todo>())
    val todos: StateFlow<List<Todo>>
        get() = _todos

    fun createTodo(title: String) = addTodo(Todo(title))

    fun toggleCheckbox(todoId: UUID) {
        val item = _todos.value.find { it.id == todoId } ?: return
        item.isCompleted = !item.isCompleted
        _todos.value = _todos.value.sortedBy { it.isCompleted }
    }

    fun removeTodo(todoId: UUID) {
        val item = _todos.value.find { it.id == todoId } ?: return
        _todos.value = _todos.value.minus(item)
    }

    private fun addTodo(todo: Todo) {
        _todos.value = listOf(todo).plus(_todos.value)
    }
}

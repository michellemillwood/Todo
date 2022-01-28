package se.millwood.todo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.data.TodoRepository
import se.millwood.todo.todolist.Todo
import java.util.*

class TodoViewModel(context: Context) : ViewModel() {

    private val repository = TodoRepository(context)

    val todos: Flow<List<Todo>> = repository.todos

    fun createTodo(title: String, description: String) {
        addTodo(Todo(title, description))
    }

    fun setIsCompleted(todoId: UUID, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.setIsCompleted(todoId, isCompleted)
        }
    }

    fun removeTodo(todoId: UUID) {
        viewModelScope.launch {
            repository.removeTodo(todoId)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    suspend fun fetchTodo(todoId: UUID) = repository.fetchTodo(todoId)

    private fun addTodo(todo: Todo) {
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }
}

class TodoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

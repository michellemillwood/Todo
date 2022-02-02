package se.millwood.todo.card

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import se.millwood.todo.data.Repository
import se.millwood.todo.data.Todo
import java.util.*

class TodoEditViewModel(context: Context, arguments: Bundle) : ViewModel() {

    private val repository = Repository(context)

    private val editArgs = arguments.getParcelable<CardFragment.TodoEditArguments>(
        CardFragment.TODO_EDIT_ARGUMENTS
    )
    private val cardId = editArgs?.cardId
    val todoId = editArgs?.todoId

    fun createTodo(
        title: String
    ) = addTodo(Todo(title, UUID.fromString(cardId)))

    private fun addTodo(todo: Todo) {
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    suspend fun fetchTodo() = repository.fetchTodo(UUID.fromString(todoId))
}

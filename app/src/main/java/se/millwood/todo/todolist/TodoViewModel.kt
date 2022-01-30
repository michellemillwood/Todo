package se.millwood.todo.todolist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.cardlist.Card
import se.millwood.todo.data.Repository
import java.util.*

class TodoViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    val todos: Flow<List<Todo>> = repository.todos
    val cards: Flow<List<Card>> = repository.cards

    fun addCard(
        title: String,
        cardId: UUID
    ) = viewModelScope.launch {
        repository.addCard(Card(title, cardId))
    }

    fun createTodo(
        title: String,
        description: String,
        cardId: UUID
    ) = addTodo(Todo(title, description, cardId))

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

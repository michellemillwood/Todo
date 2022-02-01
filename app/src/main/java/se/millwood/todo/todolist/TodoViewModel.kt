package se.millwood.todo.todolist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.cardlist.Card
import se.millwood.todo.cardlist.CardViewModel
import se.millwood.todo.data.Repository
import java.util.*

class TodoViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    fun addCard(
        title: String,
        cardId: UUID
    ) = viewModelScope.launch {
        repository.addCard(
            Card(title, cardId)
        )
    }

    suspend fun fetchCard(cardId: UUID) = repository.fetchCard(cardId)

    fun updateCard(card: Card) {
        viewModelScope.launch {
            repository.updateCard(card)
        }
    }

    fun createTodo(
        title: String,
        cardId: UUID
    ) = addTodo(Todo(title, cardId))

    private fun addTodo(todo: Todo) {
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }

    fun getTodos(cardId: UUID) = repository.getTodos(cardId)

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

}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(context) as T
        }
        else if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

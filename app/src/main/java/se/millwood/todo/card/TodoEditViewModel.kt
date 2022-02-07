package se.millwood.todo.card

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
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

    suspend fun createTodo(title: String) {
        repository.addTodo(
            cardId = UUID.fromString(cardId),
            todo = Todo(
                title = title,
                cardId = UUID.fromString(cardId)
            )
        )
    }

    suspend fun updateTodo(title: String) {
        repository.updateTodo(
            cardId = UUID.fromString(cardId),
            todoId = UUID.fromString(todoId),
            title = title
        )
    }

    suspend fun getTodoTitle() = repository.getTodoTitle(UUID.fromString(todoId))
}

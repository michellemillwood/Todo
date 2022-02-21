package se.millwood.todo.todoedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import se.millwood.todo.card.CardFragment
import se.millwood.todo.data.Repository
import se.millwood.todo.data.Todo
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val editArgs = savedStateHandle.get<CardFragment.TodoEditArguments>(
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

    suspend fun updateTodoTitle(title: String) {
        repository.updateTodoTitle(
            cardId = UUID.fromString(cardId),
            todoId = UUID.fromString(todoId),
            title = title
        )
    }

    suspend fun getTodoTitle() = repository.getTodoTitle(UUID.fromString(todoId))
}

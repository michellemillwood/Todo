package se.millwood.todo.todoedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import se.millwood.todo.card.CardFragment
import se.millwood.todo.data.Repository
import se.millwood.todo.data.Todo
import java.time.Instant
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
    var alarm: Instant? = null

    fun createTodo(title: String) {
        viewModelScope.launch {
            repository.addTodo(
                cardId = UUID.fromString(cardId),
                todo = Todo(
                    title = title,
                    cardId = UUID.fromString(cardId),
                    alarmTime = alarm
                )
            )
        }
    }

    fun updateTodoTitleAndAlarm(title: String) {
        viewModelScope.launch {
            repository.updateTodoTitleAndAlarm(
                cardId = UUID.fromString(cardId),
                todoId = UUID.fromString(todoId),
                title = title,
                alarm = alarm
            )
        }
    }

    fun getTodoAlarm() {
        viewModelScope.launch {
            alarm = repository.getTodoAlarm(UUID.fromString(todoId))
        }
    }

    suspend fun getTodoTitle() = repository.getTodoTitle(UUID.fromString(todoId))
}

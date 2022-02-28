package se.millwood.todo.todoedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import se.millwood.todo.card.CardFragment
import se.millwood.todo.data.Repository
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
    val alarm: Flow<Instant?> = repository.getTodoAlarm(UUID.fromString(todoId))

    fun updateTodoTitle(title: String) {
        viewModelScope.launch {
            repository.updateTodoTitle(
                cardId = UUID.fromString(cardId),
                todoId = UUID.fromString(todoId),
                title = title
            )
        }
    }

    fun updateTodoAlarm(alarm: Instant?) {
        viewModelScope.launch {
            repository.updateTodoAlarm(
                cardId = UUID.fromString(cardId),
                todoId = UUID.fromString(todoId),
                alarm = alarm
            )
        }
    }

    suspend fun getTodoTitle() = repository.getTodoTitle(UUID.fromString(todoId))
}

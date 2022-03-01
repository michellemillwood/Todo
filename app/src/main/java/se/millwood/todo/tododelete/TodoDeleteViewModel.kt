package se.millwood.todo.tododelete

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.millwood.todo.card.CardFragment
import se.millwood.todo.data.Repository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoDeleteViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.get<CardFragment.TodoDeleteArguments>(
        CardFragment.TODO_DELETE_ARGUMENTS
    )
    private val cardId = args?.cardId
    private val todoId = args?.todoId
    val title = args?.title

    fun deleteTodo() {
        CoroutineScope(Dispatchers.Default).launch {
            repository.deleteTodo(
                UUID.fromString(cardId),
                UUID.fromString(todoId),
            )
        }
    }
}

package se.millwood.todo.tododelete

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.millwood.todo.card.CardFragment
import se.millwood.todo.data.Repository
import java.util.*

class TodoDeleteViewModel(context: Context, arguments: Bundle) : ViewModel() {

    private val repository = Repository(context)

    private val args = arguments.getParcelable<CardFragment.TodoDeleteArguments>(
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

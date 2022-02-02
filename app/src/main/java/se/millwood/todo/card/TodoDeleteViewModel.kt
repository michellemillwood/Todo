package se.millwood.todo.card

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import se.millwood.todo.data.Repository
import java.util.*

class TodoDeleteViewModel(context: Context, arguments: Bundle) : ViewModel() {

    private val repository = Repository(context)
    private val args = arguments.getParcelable<CardFragment.TodoDeleteArguments>(CardFragment.TODO_DELETE_ARGUMENTS)
    private val todoId = args?.todoId
    val title = args?.title


    fun removeTodo() {
        viewModelScope.launch {
            repository.removeTodo(UUID.fromString(todoId))
        }
    }
}

package se.millwood.todo.tododelete

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoDeleteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = savedStateHandle.get<TodoDeleteDialogFragment.TodoDeleteArguments>(
        TodoDeleteDialogFragment.TODO_DELETE_ARGS
    )
    val todoId = args?.todoId
    val title = args?.title

}

package se.millwood.todo.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import java.util.*

class TodoDeleteDialogFragment : DialogFragment() {

    private val viewModel: TodoViewModel by activityViewModels()  {
        TodoViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            val todoId = arguments?.getString(TodoListFragment.TODO_ID_KEY)
            val title = arguments?.getString(TodoListFragment.TODO_TITLE_KEY)
            setMessage("Do you want to delete $title?")
            setPositiveButton("YES") { _, _ ->
                viewModel.removeTodo(UUID.fromString(todoId))
            }
            setNegativeButton("NO") { _, _ -> dismiss() }
        }.create()
    }
}
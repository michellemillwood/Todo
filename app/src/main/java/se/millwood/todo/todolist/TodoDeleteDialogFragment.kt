package se.millwood.todo.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import se.millwood.todo.TodoViewModel
import se.millwood.todo.TodoViewModelFactory
import java.util.*

class TodoDeleteDialogFragment : DialogFragment() {

    private val viewModel: TodoViewModel by activityViewModels()  {
        TodoViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            val todoId = arguments?.getString(ID_KEY)
            val title = arguments?.getString(TITLE_KEY)
            setMessage("Do you want to delete $title?")
            setPositiveButton("YES") { _, _ ->
                viewModel.removeTodo(UUID.fromString(todoId))
            }
            setNegativeButton("NO") { _, _ -> dismiss() }
        }.create()
    }

    companion object {
        const val ID_KEY = "todoId"
        const val TITLE_KEY = "title"
    }
}
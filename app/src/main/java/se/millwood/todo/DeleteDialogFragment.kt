package se.millwood.todo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import java.util.*

class DeleteDialogFragment : DialogFragment() {

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
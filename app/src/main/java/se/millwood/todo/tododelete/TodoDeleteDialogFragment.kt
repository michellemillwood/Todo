package se.millwood.todo.tododelete

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoDeleteDialogFragment : DialogFragment() {

    private val viewModel: TodoDeleteViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage("Do you want to delete ${viewModel.title}?")
            setPositiveButton("YES") { _, _ ->
                viewModel.deleteTodo()
            }
            setNegativeButton("NO") { _, _ -> dismiss() }
        }.create()
    }
}
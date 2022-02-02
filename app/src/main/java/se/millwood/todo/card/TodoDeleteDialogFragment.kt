package se.millwood.todo.card

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import se.millwood.todo.ViewModelFactory

class TodoDeleteDialogFragment : DialogFragment() {

    private val viewModel: TodoDeleteViewModel by viewModels  {
        ViewModelFactory(requireContext().applicationContext, arguments)
    }

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
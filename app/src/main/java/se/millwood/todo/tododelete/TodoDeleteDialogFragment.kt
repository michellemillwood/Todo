package se.millwood.todo.tododelete

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize

@AndroidEntryPoint
class TodoDeleteDialogFragment : DialogFragment() {

    private val viewModel: TodoDeleteViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage("Do you want to delete ${viewModel.title}?")
            setPositiveButton("YES") { _, _ ->
                setFragmentResult(
                    TODO_DELETE_FRAGMENT_RESULT_KEY,
                    bundleOf(
                        TODO_ID_KEY to viewModel.todoId,
                        SHOULD_DELETE_KEY to true
                    )
                )
                dismiss()
            }
            setNegativeButton("NO") { _, _ ->
                setFragmentResult(
                    TODO_DELETE_FRAGMENT_RESULT_KEY,
                    bundleOf(
                        TODO_ID_KEY to viewModel.todoId,
                        SHOULD_DELETE_KEY to false)
                )
                dismiss()
            }
        }.create()
    }

    companion object {
        const val TODO_DELETE_ARGS = "todo_delete_args"
        const val TODO_DELETE_FRAGMENT_RESULT_KEY = "todo_delete_fragment_key"
        const val SHOULD_DELETE_KEY = "should_delete"
        const val TODO_ID_KEY = "todo_id"
    }

    @Parcelize
    data class TodoDeleteArguments(
        val todoId: String,
        val title: String
    ) : Parcelable
}
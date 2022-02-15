package se.millwood.todo.todoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.millwood.todo.databinding.FragmentEditDialogBinding

@AndroidEntryPoint
class TodoEditDialogFragment : DialogFragment() {

    private val viewModel: TodoEditViewModel by viewModels()

    private lateinit var binding: FragmentEditDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDialogBinding.inflate(
            inflater,
            container,
            false
        )
        if (viewModel.todoId != null) {
            useUpdateTodoDialog()
        }
        else {
            useCreateTodoDialog()
        }
        return binding.root
    }

    private fun useUpdateTodoDialog() {
        lifecycleScope.launch {
            val todoTitle = viewModel.getTodoTitle()
            binding.editTodo.setText(todoTitle)
            binding.saveButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.updateTodo(
                        title = binding.editTodo.text.toString(),
                    )
                }
                dismiss()
            }
        }
    }

    private fun useCreateTodoDialog() {
        binding.saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.createTodo(
                    title = binding.editTodo.text.toString()
                )
            }
            dismiss()
        }
    }
}
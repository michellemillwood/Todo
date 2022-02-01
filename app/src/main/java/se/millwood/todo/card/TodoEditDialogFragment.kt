package se.millwood.todo.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import se.millwood.todo.ViewModelFactory
import se.millwood.todo.databinding.FragmentEditDialogBinding

class TodoEditDialogFragment : DialogFragment() {

    private val viewModel: TodoEditViewModel by viewModels  {
        ViewModelFactory(requireContext().applicationContext, this)
    }
    private lateinit var binding: FragmentEditDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDialogBinding.inflate(inflater, container, false)
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
            val todo = viewModel.fetchTodo()
            binding.editTodo.setText(todo.title)
            binding.saveButton.setOnClickListener {
                viewModel.updateTodo(
                    todo.copy(
                        title = binding.editTodo.text.toString(),
                    )
                )
                dismiss()
            }
        }
    }

    private fun useCreateTodoDialog() {
        binding.saveButton.setOnClickListener {
            viewModel.createTodo(
                title = binding.editTodo.text.toString()
            )
            dismiss()
        }
    }
}
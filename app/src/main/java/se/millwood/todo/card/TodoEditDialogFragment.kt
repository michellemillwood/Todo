package se.millwood.todo.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.millwood.todo.ViewModelFactory
import se.millwood.todo.databinding.FragmentEditDialogBinding

class TodoEditDialogFragment : DialogFragment() {

    private val viewModel: TodoEditViewModel by viewModels  {
        ViewModelFactory(requireContext().applicationContext, arguments)
    }
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
                CoroutineScope(Dispatchers.Default).launch {
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
            CoroutineScope(Dispatchers.Default).launch {
                viewModel.createTodo(
                    title = binding.editTodo.text.toString()
                )
            }
            dismiss()
        }
    }
}
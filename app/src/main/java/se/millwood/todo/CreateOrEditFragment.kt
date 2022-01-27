package se.millwood.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import se.millwood.todo.databinding.*
import java.util.*

class CreateOrEditFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels()  {
        TodoViewModelFactory(requireContext().applicationContext)
    }

    private lateinit var binding: FragmentTodoCreateOrEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoCreateOrEditBinding.inflate(inflater)

        // Edit
        if (arguments?.containsKey(ID_KEY) == true) {
            val todoId = arguments?.getString(ID_KEY)
            lifecycleScope.launch {
                val todo = viewModel.fetchTodo(UUID.fromString(todoId))
                populateTodoDetails(todo.title, todo.description)
                setupUpdateTodoButton(todo)
            }
            binding.buttonSave.text = "SAVE"
        }
        // Create new
        else {
            setupAddNewTodoButton()
            binding.buttonSave.text = "ADD"
        }
        setupUpButton()
        return binding.root
    }

    private fun populateTodoDetails(title: String, description: String) {
        binding.title.setText(title)
        binding.description.setText(description)
    }

    private fun setupAddNewTodoButton() {
        binding.buttonSave.setOnClickListener {
            viewModel.createTodo(
                binding.title.text.toString(),
                binding.description.text.toString()
            )
            findNavController().popBackStack()
        }
    }

    private fun setupUpdateTodoButton(todo: Todo) {
        binding.buttonSave.setOnClickListener {
            viewModel.updateTodo(Todo(
                title = binding.title.text.toString(),
                description = binding.description.text.toString(),
                isCompleted = todo.isCompleted,
                id = todo.id
                )
            )
            findNavController().popBackStack()
        }
    }


    private fun setupUpButton() {
        binding.fragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val ID_KEY = "id"
    }

}
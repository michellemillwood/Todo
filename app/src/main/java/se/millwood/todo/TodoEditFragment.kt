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

class TodoEditFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels()  {
        TodoViewModelFactory(requireContext().applicationContext)
    }

    private lateinit var binding: FragmentTodoEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoEditBinding.inflate(inflater)
        if (arguments?.containsKey(ID_KEY) == true) {
            useEditView()
        }
        else {
            useAddNewView()
        }
        setupUpButton()
        return binding.root
    }

    private fun useAddNewView() {
        setupAddNewTodoButton()
        binding.buttonSave.text = "ADD"
    }

    private fun useEditView() {
        val todoId = arguments?.getString(ID_KEY)
        lifecycleScope.launch {
            val todo = viewModel.fetchTodo(UUID.fromString(todoId))
            populateTodoDetails(todo.title, todo.description)
            setupUpdateTodoButton(todo)
        }
        binding.buttonSave.text = "SAVE"
    }

    private fun populateTodoDetails(title: String, description: String) {
        binding.title.setText(title)
        binding.todo1.setText(description)
    }

    private fun setupUpdateTodoButton(todo: Todo) {
        binding.buttonSave.setOnClickListener {
            viewModel.updateTodo(
                todo.copy(
                    title = binding.title.text.toString(),
                    description = binding.todo1.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }

    private fun setupAddNewTodoButton() {
        binding.buttonSave.setOnClickListener {
            viewModel.createTodo(
                binding.title.text.toString(),
                binding.todo1.text.toString()
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
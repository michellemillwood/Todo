package se.millwood.todo.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import se.millwood.todo.cardlist.CardListFragment
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

        val cardId = arguments?.getString(CardListFragment.CARD_ID_KEY)
        val cardUUID = UUID.fromString(cardId)

        if (arguments?.containsKey(TODO_ID_KEY) == true) {
            useEditView()
        }
        else {
            useAddNewView(cardUUID)
        }
        setupUpButton()
        return binding.root
    }

    private fun useAddNewView(cardId: UUID) {
        setupAddNewTodoButton(cardId)
        binding.buttonSave.text = "ADD"
    }

    private fun useEditView() {
        val todoId = arguments?.getString(TODO_ID_KEY)
        lifecycleScope.launch {
            val todo = viewModel.fetchTodo(UUID.fromString(todoId))
            populateTodoDetails(todo.title)
            setupUpdateTodoButton(todo)
        }
        binding.buttonSave.text = "SAVE"
    }

    private fun populateTodoDetails(title: String) {
        binding.title.setText(title)
    }

    private fun setupUpdateTodoButton(todo: Todo) {
        binding.buttonSave.setOnClickListener {
            viewModel.updateTodo(
                todo.copy(
                    title = binding.title.text.toString(),
                )
            )
            findNavController().popBackStack()
        }
    }

    private fun setupAddNewTodoButton(cardId: UUID) {
        binding.buttonSave.setOnClickListener {
            viewModel.createTodo(
                binding.title.text.toString(),
                cardId
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
        const val TODO_ID_KEY = "todo_id"
    }

}
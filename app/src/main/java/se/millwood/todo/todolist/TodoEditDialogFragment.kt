package se.millwood.todo.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import se.millwood.todo.cardlist.CardListFragment
import se.millwood.todo.databinding.*
import java.util.*

class TodoEditDialogFragment : DialogFragment() {

    private val viewModel: TodoViewModel by activityViewModels()  {
        ViewModelFactory(requireContext().applicationContext)
    }
    private lateinit var binding: FragmentEditDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDialogBinding.inflate(inflater, container, false)
        if (arguments?.containsKey(TodoListFragment.TODO_ID_KEY) == true) {
            useUpdateTodoDialog()
        }
        else {
            useCreateTodoDialog()
        }
        return binding.root
    }

    private fun useUpdateTodoDialog() {
        val todoId = arguments?.getString(TodoListFragment.TODO_ID_KEY)
        lifecycleScope.launch {
            val todo = viewModel.fetchTodo(UUID.fromString(todoId))
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
        val cardId = arguments?.getString(CardListFragment.CARD_ID_KEY)
        val cardUUID = UUID.fromString(cardId)
        binding.saveButton.setOnClickListener {
            viewModel.createTodo(
                title = binding.editTodo.text.toString(),
                cardId = cardUUID
            )
            dismiss()
        }
    }



}
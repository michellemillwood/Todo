package se.millwood.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.databinding.FragmentTodoListBinding
import java.util.*

class TodoListFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels()

    private val adapter = TodoAdapter { todoId, action ->
        updateTodo(todoId, action)
    }

    private lateinit var binding: FragmentTodoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        setupCreateButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.todos.collect { todos ->
                adapter.submitList(todos)
            }
        }
    }

    private fun setupCreateButton() {
        binding.fab.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.addTodoFragment)
        }
    }

    private fun updateTodo(todoId: UUID, action: TodoAdapter.Action) {
        when (action) {
            TodoAdapter.Action.CHECK_TOGGLE -> viewModel.toggleCheckbox(todoId)
            TodoAdapter.Action.REMOVE -> viewModel.removeTodo(todoId)
        }
    }
}
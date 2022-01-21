package se.millwood.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.databinding.FragmentTodoListBinding
import java.util.*

class TodoListFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels()

    private val adapter = TodoAdapter { todoId, action ->
        handleTodoClicked(todoId, action)
    }

    private lateinit var binding: FragmentTodoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        setupCreateTodoFab()
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

    private fun setupCreateTodoFab() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.addTodoFragment)
        }
    }

    private fun handleTodoClicked(todoId: UUID, action: TodoAdapter.Action) {
        when (action) {
            TodoAdapter.Action.CHECK_TOGGLE -> viewModel.toggleCheckbox(todoId)
            TodoAdapter.Action.REMOVE -> viewModel.removeTodo(todoId)
            TodoAdapter.Action.SEE_TODO_DETAILS -> {
                val bundle = bundleOf(TodoDetailsFragment.TODO_ID_KEY to todoId.toString())
                findNavController().navigate(R.id.todoDetailsFragment, bundle)
            }
        }
    }
}
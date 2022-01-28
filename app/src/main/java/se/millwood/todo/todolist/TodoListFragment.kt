package se.millwood.todo.todolist

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
import se.millwood.todo.*
import se.millwood.todo.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels() {
        TodoViewModelFactory(requireContext().applicationContext)
    }

    private val adapter: TodoAdapter by lazy {
        TodoAdapter(
            onItemCheck = viewModel::setIsCompleted,
            onItemDelete = { todoId, title ->
                val bundle = bundleOf(
                    TodoDeleteDialogFragment.ID_KEY to todoId.toString(),
                    TodoDeleteDialogFragment.TITLE_KEY to title
                )
                findNavController().navigate(R.id.todoDeleteDialogFragment, bundle)
            },
            onItemEdit = { todoId ->
                val bundle = bundleOf(
                    TodoEditFragment.ID_KEY to todoId.toString()
                )
                findNavController().navigate(R.id.todoEditFragment, bundle)
            }
        )
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
            findNavController().navigate(R.id.todoEditFragment)
        }
    }
}


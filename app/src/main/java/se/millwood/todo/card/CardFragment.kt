package se.millwood.todo.card

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.ViewModelFactory
import se.millwood.todo.databinding.FragmentCardBinding

class CardFragment : Fragment() {

    private val viewModel: CardViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext, this)
    }
    private lateinit var binding: FragmentCardBinding

    private val adapter: TodoAdapter by lazy {
        TodoAdapter(
            onItemCheck = viewModel::setIsCompleted,
            onItemDelete = { todoId, title ->
                val bundle = bundleOf(
                    TODO_DELETE_ARGUMENTS to TodoDeleteArguments(todoId.toString(), title)
                )
                findNavController().navigate(R.id.todoDeleteDialogFragment, bundle)
            },
            onItemEdit = { todoId, cardId ->
                val bundle = bundleOf(
                    TODO_EDIT_ARGUMENTS to TodoEditArguments(cardId.toString(), todoId.toString())
                )
                findNavController().navigate(R.id.todoEditDialogFragment, bundle)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        getCard()
        setupCreateTodoFab(viewModel.cardId)
        setupUpButton()
        return binding.root
    }

    private fun getCard() {
        lifecycleScope.launch {
            viewModel.card.collect { card ->
                binding.cardTitle.setText(card.title)
                showTodos()
            }
        }
    }

    override fun onDestroyView() {
        saveCard()
        super.onDestroyView()
    }

    private fun saveCard() {
        lifecycleScope.launch {
            viewModel.card.collect { card ->
                viewModel.updateCard(
                    card.copy(
                        title = binding.cardTitle.text.toString()
                    )
                )
            }
        }
    }

    private fun showTodos() {
        lifecycleScope.launch {
            viewModel.getTodos().collect { todos ->
                adapter.submitList(todos)
            }
        }
    }

    private fun setupCreateTodoFab(cardId: String?) {
        if (cardId == null) return
        binding.fab.setOnClickListener {
            val bundle = bundleOf(
                TODO_EDIT_ARGUMENTS to TodoEditArguments(cardId, null)
            )
            findNavController().navigate(R.id.todoEditDialogFragment, bundle)
        }
    }

    private fun setupUpButton() {
        binding.listFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val TODO_DELETE_ARGUMENTS = "todo_delete_args"
        const val TODO_EDIT_ARGUMENTS = "todo_edit_args"
    }

    @Parcelize
    data class TodoDeleteArguments(
        val todoId: String,
        val title: String
    ) : Parcelable

    @Parcelize
    data class TodoEditArguments(
        val cardId: String,
        val todoId: String?
    ) : Parcelable
}

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
import se.millwood.todo.cardlist.Card
import se.millwood.todo.cardlist.CardListFragment
import se.millwood.todo.databinding.FragmentTodoListBinding
import java.util.*

class TodoListFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels() {
        TodoViewModelFactory(requireContext().applicationContext)
    }
    private lateinit var binding: FragmentTodoListBinding

    private val adapter: TodoAdapter by lazy {
        TodoAdapter(
            onItemCheck = viewModel::setIsCompleted,
            onItemDelete = { todoId, title ->
                val bundle = bundleOf(
                    TODO_ID_KEY to todoId.toString(),
                    TODO_TITLE_KEY to title
                )
                findNavController().navigate(R.id.todoDeleteDialogFragment, bundle)
            },
            onItemEdit = { todoId, cardId ->
                val bundle = bundleOf(
                    TODO_ID_KEY to todoId.toString(),
                    CardListFragment.CARD_ID_KEY to cardId.toString()
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
        binding = FragmentTodoListBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        setupUpButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.containsKey(CardListFragment.CARD_ID_KEY) == true) {
            val cardId = arguments?.getString(CardListFragment.CARD_ID_KEY)
            setupCreateTodoFab(cardId)
            lifecycleScope.launch {
                val card = viewModel.fetchCard(UUID.fromString(cardId))
                binding.cardTitle.setText(card.title)
                setupUpdateButton(card)
                showTodos(card.cardId)
            }
        }
        else {
            val card = Card()
            viewModel.addCard(card.title, card.cardId)
            setupCreateTodoFab(card.cardId.toString())
            setupUpdateButton(card)
            showTodos(card.cardId)
        }
    }

    private fun showTodos(cardId: UUID) {
        lifecycleScope.launch {
            viewModel.getTodos(cardId).collect { todos ->
                adapter.submitList(todos)
            }
        }
    }


    private fun setupCreateTodoFab(cardId: String?) {
        binding.fab.setOnClickListener {
            val bundle = bundleOf(CardListFragment.CARD_ID_KEY to cardId)
            findNavController().navigate(R.id.todoEditDialogFragment, bundle)
        }
    }

    private fun setupUpdateButton(card: Card) {
        binding.buttonSave.setOnClickListener {
            viewModel.updateCard(
                card.copy(
                    title = binding.cardTitle.text.toString())
            )
            findNavController().popBackStack()
        }
    }

    private fun setupUpButton() {
        binding.listFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val TODO_ID_KEY = "todo_id"
        const val TODO_TITLE_KEY = "title"
    }
}


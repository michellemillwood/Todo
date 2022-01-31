package se.millwood.todo.cardlist

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
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentCardListBinding
import se.millwood.todo.todolist.TodoListFragment
import se.millwood.todo.todolist.TodoViewModel
import se.millwood.todo.todolist.TodoViewModelFactory

class CardListFragment : Fragment() {

    private val adapter: CardAdapter by lazy {
        CardAdapter(
            onItemClicked = { cardId ->
                val bundle = bundleOf(
                    CARD_ID_KEY to cardId.toString(),
                )
                findNavController().navigate(R.id.todoListFragment, bundle)
            }
        )
    }

    private val viewModel: TodoViewModel by activityViewModels() {
        TodoViewModelFactory(requireContext().applicationContext)
    }

    private lateinit var binding: FragmentCardListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardListBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        setupCreateCardFab()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.cards.collect { cards ->
                adapter.submitList(cards)
            }
        }
    }

    private fun setupCreateCardFab() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.todoListFragment)
        }
    }


    companion object {
        const val CARD_ID_KEY = "card_id"
    }

}
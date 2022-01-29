package se.millwood.todo.cardlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentCardListBinding
import se.millwood.todo.todolist.TodoListFragment

class CardListFragment : Fragment() {

    private val adapter = CardAdapter()

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

    private fun setupCreateCardFab() {
        binding.fab.setOnClickListener {
            val newCard = Card()
            val bundle = bundleOf(TodoListFragment.CARD_ID_KEY to newCard.id)
            findNavController().navigate(R.id.todoListFragment, bundle)
        }
    }

}
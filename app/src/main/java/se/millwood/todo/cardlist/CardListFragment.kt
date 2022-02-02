package se.millwood.todo.cardlist

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
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.ViewModelFactory
import se.millwood.todo.data.Card
import se.millwood.todo.databinding.FragmentCardListBinding

class CardListFragment : Fragment() {

    private val viewModel: CardListViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext, this)
    }

    private val adapter: CardListAdapter by lazy {
        CardListAdapter(
            onCardClicked = { cardId ->
                val bundle = bundleOf(
                    CARD_ID_KEY to CardArguments(cardId.toString()),
                )
                findNavController().navigate(R.id.cardFragment, bundle)
            }
        )
    }

    private lateinit var binding: FragmentCardListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardListBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        setupCreateCardFab()
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.cardsWithTodos.collect { cards ->
                adapter.submitList(cards)
            }
        }
    }

    private fun setupCreateCardFab() {
        binding.fab.setOnClickListener {
            val card = Card()
            viewModel.addCard(
                title = card.title,
                cardId = card.cardId
            )
            val bundle = bundleOf(CARD_ID_KEY to CardArguments(card.cardId.toString()))
            findNavController().navigate(R.id.cardFragment, bundle)
        }
    }

    @Parcelize
    data class CardArguments(
        val cardId: String
    ) : Parcelable

    companion object {
        const val CARD_ID_KEY = "card_id"
    }
}
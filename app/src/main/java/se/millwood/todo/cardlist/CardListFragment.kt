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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentCardListBinding
import se.millwood.todo.uitools.SwipeHandler

@AndroidEntryPoint
class CardListFragment : Fragment() {

    private val viewModel: CardListViewModel by viewModels()

    private val adapter: CardListAdapter by lazy {
        CardListAdapter(
            onCardClicked = { cardId ->
                val bundle = bundleOf(
                    CARD_ID_KEY to CardArguments(
                        cardId.toString()
                    ),
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
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            LinearLayoutManager.VERTICAL
        )
        val swipeHandler = SwipeHandler { position ->
            viewModel.deleteCardWithTodos(adapter.currentList[position])
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerView)
        setupCreateCardFab()
        setupSettingsButton()
        return binding.root
    }

    private fun setupSettingsButton() {
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getCardsWithTodos().collect { cards ->
                adapter.submitList(cards)
            }
        }
    }

    private fun setupCreateCardFab() {
        binding.fab.setOnClickListener {
            val cardId = viewModel.createCard()
            val bundle = bundleOf(
                CARD_ID_KEY to CardArguments(
                    cardId.toString()
                )
            )
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
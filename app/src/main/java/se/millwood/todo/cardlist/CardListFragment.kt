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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.ViewModelFactory
import se.millwood.todo.databinding.FragmentCardListBinding

class CardListFragment : Fragment() {

    private val viewModel: CardListViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext, arguments)
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

    private val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder,
            swipeDir: Int
        ) {
            val position = viewHolder.adapterPosition
            viewModel.deleteCardWithTodos(adapter.currentList[position])
        }
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
        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(binding.recyclerView)
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
            viewModel.cardsWithTodos.collect { cards ->
                adapter.submitList(cards)
            }
        }
    }

    private fun setupCreateCardFab() {
        binding.fab.setOnClickListener {
            val cardId = viewModel.createCard()
            val bundle = bundleOf(CARD_ID_KEY to CardArguments(cardId.toString()))
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
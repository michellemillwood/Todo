package se.millwood.todo.cardlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import se.millwood.todo.data.CardWithTodos
import se.millwood.todo.databinding.ItemCardBinding
import java.util.*

class CardListAdapter(
    val onCardClicked: (cardId: UUID) -> Unit
) : ListAdapter<CardWithTodos, CardListAdapter.CardViewHolder>(DiffCallback) {

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cardWithTodos: CardWithTodos) {
            binding.cardTitle.text = cardWithTodos.card.title
            binding.itemCard.setOnClickListener {
                onCardClicked(cardWithTodos.card.cardId)
            }
            if (cardWithTodos.todos.isNotEmpty()) {
                cardWithTodos.todos.take(5).forEach {
                    val todoTextView = TextView(binding.root.context).apply {
                        text = it.title
                        binding.gradient.visibility = View.VISIBLE
                    }
                    binding.todos.addView(todoTextView)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        return CardViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        val cardWithTodos = getItem(position)
        holder.bind(cardWithTodos)
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    object DiffCallback : DiffUtil.ItemCallback<CardWithTodos>() {
        override fun getChangePayload(oldItem: CardWithTodos, newItem: CardWithTodos): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

        override fun areItemsTheSame(
            oldItem: CardWithTodos,
            newItem: CardWithTodos
        )
        = oldItem.card.cardId == newItem.card.cardId

        override fun areContentsTheSame(
            oldItem: CardWithTodos,
            newItem: CardWithTodos
        )
        = oldItem == newItem
    }

}




package se.millwood.todo.cardlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.request.ImageRequest
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
                Coil.enqueue(
                    ImageRequest.Builder(itemView.context)
                        .data(cardWithTodos.card.imageUrl)
                        .target(binding.cardImage)
                        .build()
                )
            binding.itemCard.setOnClickListener {
                onCardClicked(
                    cardWithTodos.card.cardId
                )
            }
            if (cardWithTodos.todos.size < 6) {
                binding.gradient.visibility = View.GONE
            }
            else {
                binding.gradient.visibility = View.VISIBLE
            }
            cardWithTodos.todos.take(5).forEach {
                val todoTextView = TextView(binding.root.context).apply {
                    text = it.title
                    textSize = 16F
                }
                binding.todos.addView(todoTextView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardListAdapter.CardViewHolder {
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




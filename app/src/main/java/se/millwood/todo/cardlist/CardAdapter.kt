package se.millwood.todo.cardlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import se.millwood.todo.databinding.ItemCardBinding
import java.util.*

class CardAdapter(
    val onCardClicked: (cardId: UUID) -> Unit
) : ListAdapter<Card, CardAdapter.CardViewHolder>(DiffCallback) {

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.cardTitle.text = card.title
            binding.itemCard.setOnClickListener {
                onCardClicked(card.cardId)
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
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<Card>() {

        override fun areItemsTheSame(
            oldItem: Card,
            newItem: Card
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Card,
            newItem: Card
        ) = oldItem == newItem
    }
}
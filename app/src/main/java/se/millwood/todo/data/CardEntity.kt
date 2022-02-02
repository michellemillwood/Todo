package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "card")
class CardEntity(
    val title: String,
    @PrimaryKey
    val cardId: UUID = UUID.randomUUID()) {

    companion object {
        fun from(card: Card): CardEntity {
            return CardEntity(
                card.title,
                card.cardId
            )
        }
    }

    fun toCard(): Card {
        return Card(
            title,
            cardId
        )
    }
}
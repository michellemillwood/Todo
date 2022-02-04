package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "card")
class CardEntity(
    val title: String,
    @PrimaryKey
    val cardId: UUID = UUID.randomUUID(),
    val timeStamp: Long = System.currentTimeMillis()
) {

    companion object {
        fun from(card: Card): CardEntity {
            return CardEntity(
                card.title,
                card.cardId,
                card.timeStamp
            )
        }
    }

    fun toCard(): Card {
        return Card(
            title,
            cardId,
            timeStamp
        )
    }
}
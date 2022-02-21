package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "card")
class CardEntity(
    val title: String,
    val imageUrl: String? = null,
    @PrimaryKey
    val cardId: UUID = UUID.randomUUID(),
    val timeStamp: Long = System.currentTimeMillis()
) {

    companion object {
        fun from(card: Card): CardEntity {
            return CardEntity(
                title = card.title,
                imageUrl = card.imageUrl,
                cardId = card.cardId,
                timeStamp = card.timeStamp
            )
        }
    }

    fun toCard(): Card {
        return Card(
            title = title,
            imageUrl = imageUrl,
            cardId = cardId,
            timeStamp = timeStamp
        )
    }
}
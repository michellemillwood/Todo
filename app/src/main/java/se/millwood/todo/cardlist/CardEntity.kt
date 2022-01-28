package se.millwood.todo.cardlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "card")
class CardEntity(
    val title: String,
    val todos: MutableList<UUID>,
    @PrimaryKey
    val id: UUID = UUID.randomUUID()) {

    companion object {
        fun from(card: Card): CardEntity {
            return CardEntity(
                card.title,
                card.todos,
                card.id
            )
        }
    }

    fun toCard(): Card {
        return Card(
            title,
            todos,
            id
        )
    }
}
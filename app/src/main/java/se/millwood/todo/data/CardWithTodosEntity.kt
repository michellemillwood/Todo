package se.millwood.todo.data

import androidx.room.Embedded
import androidx.room.Relation

data class CardWithTodosEntity(
    @Embedded
    val card: CardEntity,
    @Relation(
        parentColumn = "cardId",
        entityColumn = "cardId"
    )
    val todos: List<TodoEntity>
)

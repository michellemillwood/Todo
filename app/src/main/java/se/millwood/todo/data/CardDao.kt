package se.millwood.todo.data

import androidx.room.Query

interface CardDao {

    @Query(
        "SELECT * FROM card " +
                "JOIN todo ON card.cardId = todo.cardId"
    )
    fun loadCardAndTodos(): Map<CardEntity, List<TodoEntity>>
}
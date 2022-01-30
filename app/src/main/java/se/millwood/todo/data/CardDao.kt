package se.millwood.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import se.millwood.todo.cardlist.Card

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    fun getCards(): Flow<List<CardEntity>>

    @Insert
    suspend fun addCard(cardEntity: CardEntity)

    @Query(
        "SELECT * FROM card " +
                "JOIN todo ON card.cardId = todo.cardId"
    )
    fun loadCardAndTodos(): Map<CardEntity, List<TodoEntity>>
}
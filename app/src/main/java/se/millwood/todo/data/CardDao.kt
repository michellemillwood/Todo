package se.millwood.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CardDao {

    @Query("SELECT * FROM card WHERE cardId = :id")
    suspend fun getCardById(id: UUID): CardEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCard(cardEntity: CardEntity)

    @Insert
    suspend fun addCard(cardEntity: CardEntity)

    @Query("SELECT title FROM card WHERE cardId = :cardId")
    suspend fun getCardTitle(cardId: UUID): String

    @Transaction
    @Query("SELECT card.title, card.cardId, card.timeStamp FROM card " +
            "LEFT JOIN todo ON todo.cardId = card.cardId " +
            "GROUP BY card.cardId " +
            "ORDER BY " +
            "CASE WHEN :sortOrder = 'LAST_EDITED' THEN timeStamp END DESC, " +
            "CASE WHEN :sortOrder = 'ALPHABETICAL' THEN LOWER(card.title) END ASC, " +
            "CASE WHEN :sortOrder = 'TODO_LIST_SIZE' THEN COUNT(todo.cardId) END DESC")
    fun getCardsWithTodos(sortOrder: String): Flow<List<CardWithTodosEntity>>

    @Query("DELETE FROM card WHERE cardId = :cardId")
    suspend fun deleteCard(cardId: UUID)

    @Query("UPDATE card SET timeStamp = :timeStamp WHERE cardId = :cardId")
    suspend fun updateCardTimeStamp(cardId: UUID, timeStamp: Long)
}

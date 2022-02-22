package se.millwood.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CardDao {

    @Query("SELECT * FROM card WHERE cardId = :id")
    suspend fun getCardById(id: UUID): CardEntity

    @Query("UPDATE card SET title = :title WHERE cardId = :cardId")
    suspend fun updateCardTitle(title: String, cardId: UUID)

    @Query("UPDATE card SET imageUrl = :imageUri WHERE cardId = :cardId")
    suspend fun updateCardImage(cardId: UUID, imageUri: String)

    @Insert
    suspend fun addCard(cardEntity: CardEntity)

    @Query("SELECT title FROM card WHERE cardId = :cardId")
    suspend fun getCardTitle(cardId: UUID): String

    @Transaction
    @Query("SELECT card.title, card.imageUrl, card.cardId, card.timeStamp FROM card " +
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
    suspend fun updateCardTimeStamp(cardId: UUID, timeStamp: Long = System.currentTimeMillis())

    @Query("SELECT imageUrl FROM card WHERE cardId = :cardId")
    fun getCardImage(cardId: UUID): Flow<String?>
}

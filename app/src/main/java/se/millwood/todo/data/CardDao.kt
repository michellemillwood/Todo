package se.millwood.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    fun getCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM card WHERE cardId = :id")
    suspend fun getCardById(id: UUID): CardEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCard(cardEntity: CardEntity)

    @Insert
    suspend fun addCard(cardEntity: CardEntity)

    @Transaction
    @Query("SELECT * FROM card")
    fun loadCardAndTodos(): Flow<List<CardWithTodosEntity>>

    @Query("DELETE FROM card WHERE cardId = :cardId")
    suspend fun deleteCard(cardId: UUID)
}

package se.millwood.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY isCompleted")
    fun getTodos(): Flow<List<Todo>>

    @Query("UPDATE todo SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun toggleCheckbox(id: UUID, isCompleted: Boolean)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun delete(id: UUID)

}
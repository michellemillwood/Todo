package se.millwood.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY isCompleted")
    fun getTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: UUID): TodoEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todoEntity: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoEntity: TodoEntity)

    @Query("UPDATE todo SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun setIsCompleted(id: UUID, isCompleted: Boolean)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun delete(id: UUID)

}
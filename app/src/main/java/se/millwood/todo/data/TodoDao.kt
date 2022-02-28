package se.millwood.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.util.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo " +
            "WHERE cardId = :cardId " +
            "ORDER BY isCompleted")
    fun getTodos(cardId: UUID): Flow<List<TodoEntity>>

    @Query("SELECT todoTitle FROM todo WHERE todoId = :todoId")
    suspend fun getTodoTitle(todoId: UUID): String

    @Query("SELECT alarmTime FROM todo WHERE todoId = :todoId")
    fun getTodoAlarm(todoId: UUID): Flow<Instant?>

    @Query("UPDATE todo SET alarmTime = :alarmTime WHERE todoId = :todoId")
    suspend fun setTodoAlarm(todoId: UUID, alarmTime: Instant)

    @Query("UPDATE todo SET todoTitle = :title WHERE todoId = :todoId")
    suspend fun updateTodoTitle(todoId: UUID, title: String)

    @Query("UPDATE todo SET alarmTime = :alarm WHERE todoId = :todoId")
    suspend fun updateTodoAlarm(todoId: UUID, alarm: Instant?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todoEntity: TodoEntity)

    @Query("UPDATE todo SET isCompleted = :isCompleted WHERE todoId = :todoId")
    suspend fun updateTodoIsCompleted(todoId: UUID, isCompleted: Boolean)

    @Query("DELETE FROM todo WHERE todoId = :id")
    suspend fun deleteTodo(id: UUID)

    @Query("DELETE FROM todo WHERE cardId = :cardId")
    suspend fun deleteCardTodos(cardId: UUID)
}
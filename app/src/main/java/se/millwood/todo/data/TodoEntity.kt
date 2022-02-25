package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "todo")
data class TodoEntity(
    val todoTitle: String,
    val cardId: UUID,
    val alarmTime: Instant? = null,
    val isCompleted: Boolean = false,
    @PrimaryKey
    val todoId: UUID = UUID.randomUUID()
) {

    companion object {
        fun from(todo: Todo): TodoEntity {
            return TodoEntity(
                todoTitle = todo.title,
                cardId = todo.cardId,
                alarmTime = todo.alarmTime,
                isCompleted = todo.isCompleted,
                todoId = todo.todoId
            )
        }
    }

    fun toTodo(): Todo {
        return Todo(
            title = todoTitle,
            cardId = cardId,
            alarmTime = alarmTime,
            isCompleted = isCompleted,
            todoId = todoId
        )
    }
}
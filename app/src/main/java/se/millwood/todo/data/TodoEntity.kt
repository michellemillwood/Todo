package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo")
data class TodoEntity(
    val title: String,
    val cardId: UUID,
    var isCompleted: Boolean = false,
    @PrimaryKey
    val todoId: UUID = UUID.randomUUID()
) {

    companion object {
        fun from(todo: Todo): TodoEntity {
            return TodoEntity(
                todo.title,
                todo.cardId,
                todo.isCompleted,
                todo.todoId
            )
        }
    }

    fun toTodo(): Todo {
        return Todo(
            title,
            cardId,
            isCompleted,
            todoId
        )
    }
}
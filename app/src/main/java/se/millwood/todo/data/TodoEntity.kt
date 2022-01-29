package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import se.millwood.todo.cardlist.Card
import se.millwood.todo.todolist.Todo
import java.util.*

@Entity(tableName = "todo")
data class TodoEntity(
    val title: String,
    val description: String,
    val cardId: UUID,
    var isCompleted: Boolean = false,
    @PrimaryKey
    val todoId: UUID = UUID.randomUUID()
) {

    companion object {
        fun from(todo: Todo): TodoEntity {
            return TodoEntity(
                todo.title,
                todo.description,
                todo.cardId,
                todo.isCompleted,
                todo.todoId
            )
        }
    }

    fun toTodo(): Todo {
        return Todo(
            title,
            description,
            cardId,
            isCompleted,
            todoId
        )
    }
}
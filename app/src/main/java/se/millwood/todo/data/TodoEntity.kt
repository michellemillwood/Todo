package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import se.millwood.todo.Todo
import java.util.*

@Entity(tableName = "todo")
data class TodoEntity(
    val title: String,
    val description: String,
    var isCompleted: Boolean = false,
    @PrimaryKey val id: UUID = UUID.randomUUID()
) {

    companion object {
        fun from(todo: Todo): TodoEntity {
            return TodoEntity(
                todo.title,
                todo.description,
                todo.isCompleted,
                todo.id
            )
        }
    }

    fun toTodo(): Todo {
        return Todo(title, description, isCompleted, id)
    }
}
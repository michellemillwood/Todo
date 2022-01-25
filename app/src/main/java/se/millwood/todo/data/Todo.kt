package se.millwood.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo")
data class Todo(
    val title: String,
    val description: String,
    @PrimaryKey val id: UUID = UUID.randomUUID()
) {
    // This is placed in the body so that it is excluded from the == comparison
    var isCompleted: Boolean = false
}
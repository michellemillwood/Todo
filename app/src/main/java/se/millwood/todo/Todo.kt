package se.millwood.todo

import androidx.room.Entity
import java.util.*

@Entity
data class Todo(
    val title: String,
    val description: String,
    var isCompleted: Boolean = false,
    val id: UUID = UUID.randomUUID()
)
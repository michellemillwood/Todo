package se.millwood.todo.data

import java.time.Instant
import java.util.*

data class Todo(
    val title: String,
    val cardId: UUID,
    val alarmTime: Instant? = null,
    val isCompleted: Boolean = false,
    val todoId: UUID = UUID.randomUUID()
)

package se.millwood.todo

import java.util.*

data class Todo(
    val title: String,
    var isCompleted: Boolean = false,
    val id: UUID = UUID.randomUUID()
)
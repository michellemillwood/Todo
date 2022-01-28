package se.millwood.todo.todolist

import java.util.*

data class Todo(
    val title: String,
    val description: String,
    var isCompleted: Boolean = false,
    val id: UUID = UUID.randomUUID()
)
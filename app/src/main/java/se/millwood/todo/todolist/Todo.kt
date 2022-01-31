package se.millwood.todo.todolist

import java.util.*

data class Todo(
    val title: String,
    val cardId: UUID,
    var isCompleted: Boolean = false,
    val todoId: UUID = UUID.randomUUID()
)
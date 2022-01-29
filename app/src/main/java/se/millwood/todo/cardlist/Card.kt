package se.millwood.todo.cardlist

import java.util.*

data class Card(
    val title: String = "",
    val todos: MutableList<UUID> = mutableListOf(),
    val id: UUID = UUID.randomUUID()
)
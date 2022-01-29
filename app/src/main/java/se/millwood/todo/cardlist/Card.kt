package se.millwood.todo.cardlist

import java.util.*

data class Card(
    val title: String = "",
    val cardId: UUID = UUID.randomUUID()
)
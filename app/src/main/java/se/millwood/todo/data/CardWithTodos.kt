package se.millwood.todo.data

data class CardWithTodos(
    val card: Card,
    val todos: List<Todo>
) {
    companion object {
        fun from(cardWithTodosEntity: CardWithTodosEntity): CardWithTodos {
            return CardWithTodos(
                cardWithTodosEntity.card.toCard(),
                cardWithTodosEntity.todos.map { it.toTodo() }
            )
        }
    }
}
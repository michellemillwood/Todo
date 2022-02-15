package se.millwood.todo.data

import kotlinx.coroutines.flow.map
import se.millwood.todo.SortOrder
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(database: TodoDatabase) {

    private val cardDao = database.cardDao()
    private val todoDao = database.todoDao()

    fun getCardsWithTodos(sortOrder: SortOrder) =
        cardDao.getCardsWithTodos(sortOrder.name).map { cards ->
            cards.map { CardWithTodos.from(it) }
        }

    suspend fun addCard(
        card: Card
    ) = cardDao.addCard(CardEntity.from(card))

    suspend fun updateCardTitle(
        title: String,
        cardId: UUID
    ) = cardDao.updateCard(CardEntity(title, cardId))

    suspend fun getCardTitle(
        cardId: UUID
    ) = cardDao.getCardTitle(cardId)

    suspend fun deleteCardWithTodos(
        cardId: UUID
    ) {
        todoDao.deleteCardTodos(cardId)
        cardDao.deleteCard(cardId)
    }

    fun getTodos(
        cardId: UUID
    ) = todoDao.getTodos(cardId).map { todoEntity ->
        todoEntity.map { it.toTodo() }
    }

    suspend fun updateTodo(
        cardId: UUID,
        todoId: UUID,
        title: String
    ) {
        cardDao.updateCardTimeStamp(cardId, System.currentTimeMillis())
        todoDao.updateTodoTitle(todoId, title)
    }

    suspend fun addTodo(
        cardId: UUID,
        todo: Todo
    ) {
        cardDao.updateCardTimeStamp(cardId, System.currentTimeMillis())
        todoDao.addTodo(TodoEntity.from(todo))
    }

    suspend fun setIsCompleted(
        cardId: UUID,
        todoId: UUID,
        isCompleted: Boolean
    ) {
        cardDao.updateCardTimeStamp(cardId, System.currentTimeMillis())
        todoDao.updateTodoIsCompleted(todoId, isCompleted)
    }

    suspend fun getTodoTitle(
        todoId: UUID
    ): String = todoDao.getTodoTitle(todoId)

    suspend fun deleteTodo(
        cardId: UUID,
        todoId: UUID
    ) {
        cardDao.updateCardTimeStamp(cardId, System.currentTimeMillis())
        todoDao.deleteTodo(todoId)
    }
}
package se.millwood.todo.data

import android.net.Uri
import kotlinx.coroutines.flow.map
import se.millwood.todo.settings.SortOrder
import java.time.Instant
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

    fun getTodoAlarm(
        todoId: UUID
    ) = todoDao.getTodoAlarm(todoId)

    suspend fun addCard(
        card: Card
    ) = cardDao.addCard(CardEntity.from(card))

    suspend fun getTodosWithAlarms() = todoDao.getTodosWithAlarms().map {
        it.toTodo()
    }

    suspend fun updateCardImage(
        cardId: UUID,
        imageUri: Uri
    ) {
        cardDao.updateCardTimeStamp(cardId)
        cardDao.updateCardImage(cardId, imageUri.toString())
    }

    suspend fun updateCardTitle(
        title: String,
        cardId: UUID
    ) {
        cardDao.updateCardTimeStamp(cardId)
        cardDao.updateCardTitle(title, cardId)
    }

    fun getCardImage(
        cardId: UUID
    ) = cardDao.getCardImage(cardId).map {
        if (it == null) null
        else Uri.parse(it)
    }

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

    suspend fun updateTodoTitle(
        cardId: UUID,
        todoId: UUID,
        title: String,
    ) {
        cardDao.updateCardTimeStamp(cardId)
        todoDao.updateTodoTitle(todoId, title)
    }

    suspend fun updateTodoAlarm(
        cardId: UUID,
        todoId: UUID,
        alarm: Instant?
    ) {
        cardDao.updateCardTimeStamp(cardId)
        todoDao.updateTodoAlarm(todoId, alarm)
    }

    suspend fun addTodo(
        todo: Todo
    ) {
        cardDao.updateCardTimeStamp(todo.cardId)
        todoDao.addTodo(TodoEntity.from(todo))
    }

    suspend fun setIsCompleted(
        cardId: UUID,
        todoId: UUID,
        isCompleted: Boolean
    ) {
        cardDao.updateCardTimeStamp(cardId)
        todoDao.updateTodoIsCompleted(todoId, isCompleted)
    }

    suspend fun getTodoTitle(
        todoId: UUID
    ): String = todoDao.getTodoTitle(todoId)

    suspend fun deleteTodo(
        cardId: UUID,
        todoId: UUID
    ) {
        cardDao.updateCardTimeStamp(cardId)
        todoDao.deleteTodo(todoId)
    }
}
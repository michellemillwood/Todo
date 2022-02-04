package se.millwood.todo.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.millwood.todo.settings.SettingsFragment
import java.util.*

class Repository(context: Context) {

    private val cardDao = TodoDatabase.getDatabase(context).cardDao()
    private val todoDao = TodoDatabase.getDatabase(context).todoDao()

    private val cardsWithTodos: Flow<List<CardWithTodos>> = cardDao.loadCardAndTodos().map { cards ->
        cards.map { CardWithTodos.from(it) }
    }

    fun getCardWithTodos(
        sortOrder: SettingsFragment.Companion.SortOrder
    ): Flow<List<CardWithTodos>> {

        return when (sortOrder) {
            SettingsFragment.Companion.SortOrder.ALPHABETICAL -> cardsWithTodos.map {
                it.sortedBy { card -> card.card.title.lowercase() }
            }
            SettingsFragment.Companion.SortOrder.LAST_EDITED -> cardsWithTodos.map {
              it
            }
            SettingsFragment.Companion.SortOrder.TODO_LIST_SIZE -> cardsWithTodos.map {
                it.sortedByDescending { card -> card.todos.size }
            }
        }
    }

    suspend fun addCard(
        card: Card
    ) = cardDao.addCard(CardEntity.from(card))

    suspend fun updateCard(
        cardTitle: String,
        cardId: UUID
    ) = cardDao.updateCard(CardEntity(cardTitle, cardId))

    suspend fun deleteCardWithTodos(
        cardId: UUID
    ) {
        todoDao.deleteCardTodos(cardId)
        cardDao.deleteCard(cardId)
    }

    suspend fun fetchCard(
        cardId: UUID
    ): Card = cardDao.getCardById(cardId).toCard()

    fun getTodos(
        cardId: UUID
    ) = todoDao.getTodos(cardId).map { todoEntity ->
        todoEntity.map { it.toTodo() }
    }

    suspend fun updateTodo(
        todo: Todo
    ) = todoDao.updateTodo(TodoEntity.from(todo))

    suspend fun addTodo(
        todo: Todo
    ) = todoDao.addTodo(TodoEntity.from(todo))

    suspend fun setIsCompleted(
        todoId: UUID,
        isCompleted: Boolean
    ) = todoDao.setIsCompleted(todoId, isCompleted)

    suspend fun fetchTodo(
        todoId: UUID
    ): Todo = todoDao.getTodoById(todoId).toTodo()

    suspend fun deleteTodo(todoId: UUID) = todoDao.deleteTodo(todoId)
}
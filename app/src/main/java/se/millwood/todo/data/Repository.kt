package se.millwood.todo.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.millwood.todo.cardlist.Card
import se.millwood.todo.todolist.Todo
import java.util.*

class Repository(context: Context) {

    private val cardDao = TodoDatabase.getDatabase(context).cardDao()
    private val todoDao = TodoDatabase.getDatabase(context).todoDao()

    val cards: Flow<List<Card>> = cardDao.getCards().map { cardEntity ->
        cardEntity.map { it.toCard() }
    }

    fun getTodos(cardId: UUID) = todoDao.getTodos(cardId).map { todoEntity ->
        todoEntity.map { it.toTodo() }
    }

    suspend fun addCard(card: Card) = cardDao.addCard(CardEntity.from(card))

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(TodoEntity.from(todo))

    suspend fun addTodo(todo: Todo) = todoDao.addTodo(TodoEntity.from(todo))

    suspend fun setIsCompleted(todoId: UUID, isCompleted: Boolean) {
        todoDao.setIsCompleted(todoId, isCompleted)
    }

    suspend fun fetchTodo(todoId: UUID): Todo {
        val todoEntity = todoDao.getTodoById(todoId)
        return todoEntity.toTodo()
    }

    suspend fun removeTodo(todoId: UUID) {
        todoDao.delete(todoId)
    }
}
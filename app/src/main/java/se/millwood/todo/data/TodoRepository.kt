package se.millwood.todo.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.millwood.todo.Todo
import java.util.*

class TodoRepository(context: Context) {

    private val todoDao = TodoDatabase.getDatabase(context).todoDao()

    val todos: Flow<List<Todo>> = todoDao.getTodos().map { todoEntity ->
        todoEntity.map { it.toTodo() }
    }

    suspend fun addTodo(todo: Todo) = todoDao.insert(TodoEntity.from(todo))

    suspend fun toggleCheckbox(todoId: UUID, isCompleted: Boolean) {
        todoDao.setIsCompleted(todoId, isCompleted)
    }

    suspend fun removeTodo(todoId: UUID) {
        todoDao.delete(todoId)
    }


}
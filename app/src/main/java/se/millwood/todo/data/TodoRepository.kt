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

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(TodoEntity.from(todo))

    suspend fun addTodo(todo: Todo) = todoDao.insert(TodoEntity.from(todo))

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
package se.millwood.todo.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import java.util.*

class TodoRepository(context: Context) {

    private val todoDao = TodoDatabase.getDatabase(context).todoDao()

    val todos: Flow<List<Todo>> = todoDao.getTodos()

    suspend fun addTodo(todo: Todo) = todoDao.insert(todo)

    suspend fun toggleCheckbox(todoId: UUID, isCompleted: Boolean) {
        todoDao.setIsCompleted(todoId, isCompleted)
    }

    suspend fun removeTodo(todoId: UUID) {
        todoDao.delete(todoId)
    }


}
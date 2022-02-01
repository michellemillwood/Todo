package se.millwood.todo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import se.millwood.todo.databinding.ItemTodoBinding
import java.util.*

class TodoAdapter(
    val onItemCheck: (todoId: UUID, isChecked: Boolean) -> Unit,
    val onItemDelete: (todoId: UUID, title: String) -> Unit,
    val onItemEdit: (todoId: UUID, cardId: UUID) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallback) {

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) = with(binding) {
            titleTodo.text = todo.title
            checkbox.isChecked = todo.isCompleted

            checkbox.setOnCheckedChangeListener { _, isChecked ->
               onItemCheck(todo.todoId, isChecked)
            }

            deleteTodo.setOnClickListener {
                onItemDelete(todo.todoId, todo.title)
            }

            root.setOnClickListener {
                onItemEdit(todo.todoId, todo.cardId)
            }
        }

        fun unbind() {
            with(binding) {
                checkbox.setOnCheckedChangeListener(null)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        return TodoViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: TodoViewHolder) {
        holder.unbind()
        super.onViewRecycled(holder)
    }

    object DiffCallback : DiffUtil.ItemCallback<Todo>() {

        override fun areItemsTheSame(
            oldItem: Todo,
            newItem: Todo
        ) = oldItem.todoId == newItem.todoId

        override fun areContentsTheSame(
            oldItem: Todo,
            newItem: Todo
        ) = oldItem == newItem
    }
}

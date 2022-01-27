package se.millwood.todo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import se.millwood.todo.Todo
import se.millwood.todo.databinding.ItemTodoBinding
import java.util.*

class TodoAdapter(
    val onItemChecked: (id: UUID, isChecked: Boolean) -> Unit,
    val onItemRemoved: (id: UUID, title: String) -> Unit,
    val onItemEdit: (id: UUID) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallback) {

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) = with(binding) {
            titleTodo.text = todo.title
            checkbox.isChecked = todo.isCompleted

            checkbox.setOnCheckedChangeListener { _, isChecked ->
               onItemChecked(todo.id, isChecked)
            }

            deleteTodo.setOnClickListener {
                onItemRemoved(todo.id, todo.title)
            }

            root.setOnClickListener {
                onItemEdit(todo.id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        return TodoViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<Todo>() {

        override fun areItemsTheSame(
            oldItem: Todo,
            newItem: Todo
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Todo,
            newItem: Todo
        ) = oldItem == newItem
    }

}
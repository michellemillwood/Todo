package se.millwood.todo.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import se.millwood.todo.data.Todo
import se.millwood.todo.databinding.ItemTodoBinding
import java.util.*

private const val nameChangedPayload = "name_changed"
private const val checkBoxChangedPayload = "checkbox_checked"

class TodoAdapter(
    val onItemCheck: (todoId: UUID, isChecked: Boolean) -> Unit,
    val onItemDelete: (todoId: UUID, title: String) -> Unit,
    val onItemEdit: (todoId: UUID) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallback) {

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) = with(binding) {
            titleTodo.text = todo.title
            checkbox.isChecked = todo.isCompleted

            checkbox.setOnCheckedChangeListener { _, isChecked ->
               onItemCheck(
                   todo.todoId,
                   isChecked
               )
            }

            deleteTodo.setOnClickListener {
                onItemDelete(
                    todo.todoId,
                    todo.title
                )
            }

            root.setOnClickListener {
                onItemEdit(
                    todo.todoId,
                )
            }
        }
        fun bindName(todo: Todo) {
            binding.titleTodo.text = todo.title
        }

        fun bindCheckBox(todo: Todo) {
            binding.checkbox.isChecked = todo.isCompleted
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

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when {
            nameChangedPayload in payloads -> {
                holder.bindName(getItem(position))
            }
            checkBoxChangedPayload in payloads -> {
                holder.bindCheckBox(getItem(position))
            }
            else -> {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun onViewRecycled(holder: TodoAdapter.TodoViewHolder) {
        holder.unbind()
        super.onViewRecycled(holder)
    }

    object DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun getChangePayload(oldItem: Todo, newItem: Todo): Any? {
            if (oldItem == newItem) {
                return super.getChangePayload(oldItem, newItem)
            }
            if (oldItem.copy(title = "") == newItem.copy(title = "")) {
                return nameChangedPayload
            }
            if (oldItem.copy(isCompleted = true) == newItem.copy(isCompleted = true)) {
                return checkBoxChangedPayload
            }
            return super.getChangePayload(oldItem, newItem)
        }

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

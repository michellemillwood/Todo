package se.millwood.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import se.millwood.todo.databinding.ItemTodoBinding

class TodoAdapter(val updateItemsCallback: (List<Todo>) -> Unit) :
    ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback) {

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) = with(binding) {
            titleTodo.text = todo.title

            checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
               todo.isCompleted = buttonView.isChecked
               updateItemsCallback(currentList)
            }

            removeTodo.setOnClickListener {
                updateItemsCallback(currentList.minus(todo))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoAdapter.TodoViewHolder = TodoViewHolder(
        ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }


    object TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {

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
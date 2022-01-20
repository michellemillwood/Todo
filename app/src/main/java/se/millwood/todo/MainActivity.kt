package se.millwood.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import se.millwood.todo.TodoAdapter.*
import se.millwood.todo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel: TodoViewModel by viewModels()

    private val adapter = TodoAdapter { todoId, action ->
        updateTodo(todoId, action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.todos.collect { todos ->
                adapter.submitList(todos)
            }
        }
        addTodoDialog()
    }

    private fun updateTodo(todoId: UUID, action: Action) {
        when (action) {
            Action.COMPLETION_TOGGLE -> viewModel.toggleCheckbox(todoId)
            Action.REMOVE -> viewModel.removeTodo(todoId)
        }
    }


    private fun addTodoDialog() {
        binding.fab.setOnClickListener {
            val editTextTitle = EditText(this)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Title")
                .setView(editTextTitle)
                .setPositiveButton("Add") { _, _ ->
                    viewModel.createTodo(editTextTitle.text.toString())
                }
                .create()
            dialog.show()
        }
    }
}

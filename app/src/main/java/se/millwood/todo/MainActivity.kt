package se.millwood.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import se.millwood.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel: TodoViewModel by viewModels()

    private val adapter = TodoAdapter { newItems ->
        updateAdapterItems(newItems)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
        updateAdapterItems(viewModel.getTodos())
        addTodoDialog()
    }

    private fun updateAdapterItems(newTodos: List<Todo>) {
        viewModel.updateTodos(newTodos)
        adapter.submitList(viewModel.getTodos())
    }

    private fun addTodoDialog() {
        binding.fab.setOnClickListener {
            val editTextTitle = EditText(this)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Title")
                .setView(editTextTitle)
                .setPositiveButton("Add") { _, _ ->
                    viewModel.createTodo(editTextTitle.text.toString())
                    updateAdapterItems(viewModel.getTodos())
                }
                .create()
            dialog.show()
        }
    }
}

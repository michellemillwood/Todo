package se.millwood.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import se.millwood.todo.databinding.FragmentTodoDetailsBinding
import java.util.*

class TodoDetailsFragment : Fragment() {



    private val viewModel: TodoViewModel by activityViewModels()
    private lateinit var binding: FragmentTodoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoDetailsBinding.inflate(inflater)
        val todoId = UUID.fromString(arguments?.getString(TODO_ID_KEY))
        val todo = viewModel.getTodo(todoId)
        binding.todoTitle.text = todo?.title
        binding.todoDescription.text = todo?.description
        return binding.root
    }

    companion object {
        const val TODO_ID_KEY = "todo_id"
    }

}
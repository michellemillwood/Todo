package se.millwood.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import se.millwood.todo.databinding.FragmentAddTodoBinding

class AddTodoFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels()  {
        TodoViewModelFactory(requireContext().applicationContext)
    }
    private lateinit var binding: FragmentAddTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoBinding.inflate(inflater)
        setupAddButton()
        setupUpButton()
        return binding.root
    }

    private fun setupUpButton() {
        binding.addFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setupAddButton() {
        binding.buttonAdd.setOnClickListener {
            viewModel.createTodo(
                binding.title.text.toString(),
                binding.description.text.toString()
            )
            findNavController().popBackStack()
        }
    }
}
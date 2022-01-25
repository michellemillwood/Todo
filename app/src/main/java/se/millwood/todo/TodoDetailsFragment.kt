package se.millwood.todo

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import se.millwood.todo.databinding.FragmentTodoDetailsBinding
import java.util.*

class TodoDetailsFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels()  {
        TodoViewModelFactory(requireContext().applicationContext)
    }
    private lateinit var binding: FragmentTodoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoDetailsBinding.inflate(inflater)
        val title = arguments?.getString(TITLE_KEY)
        val description = arguments?.getString(DESCRIPTION_KEY)
        populateTodoDetails(title, description)
        return binding.root
    }

    private fun populateTodoDetails(
        title: String?,
        description: String?
    ) {
        binding.todoTitle.text = title
        binding.todoDescription.text = description
    }

    companion object {
        const val TITLE_KEY = "title"
        const val DESCRIPTION_KEY = "description"
    }

}
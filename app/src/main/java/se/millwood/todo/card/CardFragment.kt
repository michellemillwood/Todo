package se.millwood.todo.card

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.Coil
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentCardBinding
import se.millwood.todo.imagepicker.ImagePickerDialogFragment

@AndroidEntryPoint
class CardFragment : Fragment() {

    private val viewModel: CardViewModel by viewModels()

    private lateinit var binding: FragmentCardBinding

    private val adapter: TodoAdapter by lazy {
        TodoAdapter(
            onItemCheck = viewModel::setIsCompleted,
            onItemDelete = { todoId, title ->
                val bundle = bundleOf(
                    TODO_DELETE_ARGUMENTS to TodoDeleteArguments(
                        cardId = viewModel.cardId.toString(),
                        todoId = todoId.toString(),
                        title = title
                    )
                )
                findNavController().navigate(R.id.todoDeleteDialogFragment, bundle)
            },
            onItemEdit = { todoId ->
                val bundle = bundleOf(
                    TODO_EDIT_ARGUMENTS to TodoEditArguments(
                        cardId = viewModel.cardId.toString(),
                        todoId = todoId.toString()
                    )
                )
                findNavController().navigate(R.id.todoEditDialogFragment, bundle)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        getCardAndTodos()
        setupCreateTodoFab()
        setupImagePickerButton()
        setupUpButton()
        loadCardImage()
        setFragmentResultListener(
            ImagePickerDialogFragment.IMAGE_URI_KEY
        ) { _, bundle ->
            val imageUri = bundle.getString("imageUri")
            viewModel.updateCardImage(Uri.parse(imageUri))
        }
        return binding.root
    }

    private fun setupImagePickerButton() {
        binding.cardImage.setOnClickListener {
            findNavController().navigate(R.id.imagePickerDialogFragment)
        }
    }

    private fun getCardAndTodos() {
        lifecycleScope.launch {
            binding.cardTitle.setText(viewModel.getCardTitle())
            binding.cardTitle.doOnTextChanged { title, _, _, _ ->
                viewModel.updateCardTitle(title.toString())
            }
            viewModel.getTodos().collect { todos ->
                adapter.submitList(todos)
            }
        }
    }

    private fun loadCardImage() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.image
                .collectLatest { imageUri ->
                    Coil.execute(
                        ImageRequest.Builder(requireContext())
                            .data(imageUri ?: R.drawable.ic_baseline_add_photo_alternate_24)
                            .target(binding.cardImage)
                            .build()
                    )
                }
        }
    }

    private fun setupCreateTodoFab() {
        binding.fab.setOnClickListener {
            val todoId = viewModel.createTodo()
            val bundle = bundleOf(
                TODO_EDIT_ARGUMENTS to TodoEditArguments(
                    cardId = viewModel.cardId.toString(),
                    todoId = todoId.toString()
                )
            )
            findNavController().navigate(R.id.todoEditDialogFragment, bundle)
        }
    }

    private fun setupUpButton() {
        binding.cardFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val TODO_DELETE_ARGUMENTS = "todo_delete_args"
        const val TODO_EDIT_ARGUMENTS = "todo_edit_args"
    }

    @Parcelize
    data class TodoDeleteArguments(
        val cardId: String,
        val todoId: String,
        val title: String
    ) : Parcelable

    @Parcelize
    data class TodoEditArguments(
        val cardId: String,
        val todoId: String
    ) : Parcelable
}

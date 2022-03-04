package se.millwood.todo.card

import android.net.Uri
import android.os.Bundle
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
import androidx.recyclerview.widget.ItemTouchHelper
import coil.Coil
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import se.millwood.todo.R
import se.millwood.todo.databinding.FragmentCardBinding
import se.millwood.todo.imagepicker.ImagePickerDialogFragment
import se.millwood.todo.tododelete.TodoDeleteDialogFragment
import se.millwood.todo.todoedit.TodoEditDialogFragment
import se.millwood.todo.uitools.SwipeHandler

@AndroidEntryPoint
class CardFragment : Fragment() {

    private val viewModel: CardViewModel by viewModels()

    private lateinit var binding: FragmentCardBinding

    private val adapter: TodoAdapter by lazy {
        TodoAdapter(
            onItemCheck = viewModel::setIsCompleted,
            onItemEdit = { todoId ->
                val bundle = bundleOf(
                    TodoEditDialogFragment.TODO_EDIT_ARGS to TodoEditDialogFragment.TodoEditArguments(
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
        setupSwipeToDelete()
        setFragmentResultListener(TodoDeleteDialogFragment.TODO_DELETE_FRAGMENT_RESULT_KEY) { _, bundle ->
            val todoId = bundle.getString(TodoDeleteDialogFragment.TODO_ID_KEY)
            val shouldDelete = bundle.getBoolean(TodoDeleteDialogFragment.SHOULD_DELETE_KEY)
            if (shouldDelete) {
                if (todoId != null) {
                    viewModel.deleteTodo(todoId)
                }
            } else {
                lifecycleScope.launch {
                    viewModel.getTodos().collectLatest { todos ->
                        adapter.submitList(todos)
                    }
                }
            }
        }
        setFragmentResultListener(ImagePickerDialogFragment.IMAGE_FRAGMENT_RESULT_KEY) { _, bundle ->
            val imageUri = bundle.getString(ImagePickerDialogFragment.IMAGE_URI_KEY)
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
                TodoEditDialogFragment.TODO_EDIT_ARGS to TodoEditDialogFragment.TodoEditArguments(
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

    private fun setupSwipeToDelete() {
        val swipeHandler = SwipeHandler { position ->
            val todo = adapter.currentList[position]
            adapter.submitList(adapter.currentList.minus(todo))
            val bundle = bundleOf(
                TodoDeleteDialogFragment.TODO_DELETE_ARGS to TodoDeleteDialogFragment.TodoDeleteArguments(
                    todoId = todo.todoId.toString(),
                    title = todo.title
                )
            )
            findNavController().navigate(R.id.todoDeleteDialogFragment, bundle)
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerView)
    }


}

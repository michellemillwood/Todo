package se.millwood.todo.imagepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import se.millwood.todo.databinding.FragmentImagePickerBinding

@AndroidEntryPoint
class ImagePickerFragment : Fragment() {

    private val viewModel: ImagePickerViewModel by viewModels()

    private lateinit var binding: FragmentImagePickerBinding

    private val adapter: ImageListAdapter by lazy {
        ImageListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagePickerBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        setupUpButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImages()
    }

    private fun loadImages() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.images
                .collectLatest { images ->
                    adapter.submitList(images)
                }
        }
    }

    private fun setupUpButton() {
        binding.imageFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
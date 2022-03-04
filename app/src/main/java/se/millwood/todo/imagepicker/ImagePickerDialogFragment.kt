package se.millwood.todo.imagepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import se.millwood.todo.databinding.FragmentImagePickerBinding

@AndroidEntryPoint
class ImagePickerDialogFragment : DialogFragment() {

    private val viewModel: ImagePickerViewModel by viewModels()

    private lateinit var binding: FragmentImagePickerBinding

    private val adapter: ImageListAdapter by lazy {
        ImageListAdapter(
            onImageSelected = { imageUri ->
                setFragmentResult(
                    IMAGE_FRAGMENT_RESULT_KEY,
                    bundleOf(IMAGE_URI_KEY to imageUri.toString())
                )
                dismiss()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagePickerBinding.inflate(
            inflater,
            container,
            false
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        loadImages()
        return binding.root
    }

    private fun loadImages() {
        lifecycleScope.launch {
            viewModel.images
                .collectLatest { images ->
                    adapter.submitList(images)
                }
        }
    }

    companion object {
        const val IMAGE_FRAGMENT_RESULT_KEY = "image_fragment_key"
        const val IMAGE_URI_KEY = "image_uri_key"
    }

}
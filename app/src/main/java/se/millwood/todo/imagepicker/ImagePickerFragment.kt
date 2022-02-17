package se.millwood.todo.imagepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.Coil
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import se.millwood.todo.databinding.FragmentImagePickerBinding

@AndroidEntryPoint
class ImagePickerFragment : Fragment() {

    private lateinit var binding: FragmentImagePickerBinding

    private val viewModel: ImagePickerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.images
                .collectLatest { images ->
                    val uri = images.first()
                    Coil.execute(
                        ImageRequest.Builder(requireContext())
                            .data(uri)
                            .target(binding.image)
                            .build()
                    )
                }
        }
    }

}
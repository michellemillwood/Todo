package se.millwood.todo.imagepicker

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import se.millwood.todo.network.ImageApiService
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(apiService: ImageApiService) : ViewModel() {

    val images: Flow<List<Uri>> = flow {
        emit(apiService.getImageUris())
    }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

}
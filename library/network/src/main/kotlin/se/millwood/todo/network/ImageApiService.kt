package se.millwood.todo.network

import android.net.Uri
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageApiService @Inject constructor(retrofit: Retrofit) {
    private val api: ImageApi = retrofit.create()

    suspend fun getImageUris(): List<Uri> {
        return api.getImages().map { image ->
            Uri.parse(image.url)
        }
    }
}
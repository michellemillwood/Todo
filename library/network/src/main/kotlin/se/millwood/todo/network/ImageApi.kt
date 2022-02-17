package se.millwood.todo.network

import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Query
import se.ansman.kotshi.JsonSerializable

internal const val BASE_URL = "https://api.thecatapi.com/v1/"

internal interface ImageApi {

    @GET("images/search")
    suspend fun getImages(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 0
    ): List<Image>
}

@JsonSerializable
internal data class Image(
    @Json(name = "url")
    val url: String
)
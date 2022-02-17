package se.millwood.todo.network

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
object NetworkAdapterFactory : JsonAdapter.Factory by KotshiNetworkAdapterFactory
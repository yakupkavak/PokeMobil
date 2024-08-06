package com.example.pokemobil.repository

import com.example.pokemobil.model.Resource
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response

@ActivityScoped
class BaseRepository {

    suspend inline fun <T> fetchData(crossinline call: suspend () -> Response<T>): Resource<T> {
        val response = call()
        return try {
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null)
            } else {
                return Resource.error(null)
            }
        } catch (e: Exception) {
            return Resource.error(null)
        }
    }
}
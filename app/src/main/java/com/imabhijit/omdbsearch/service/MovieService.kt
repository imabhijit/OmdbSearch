package com.imabhijit.omdbsearch.service

import com.imabhijit.omdbsearch.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    companion object {
        private const val API_KEY = ""
    }

    @GET("?apikey=$API_KEY")
    fun getMoviesByTitle(@Query("s") title: String): Call<SearchResult>
}
package com.imabhijit.omdbsearch.service

import com.imabhijit.omdbsearch.R
import com.imabhijit.omdbsearch.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("?apikey=${R.string.API_KEY}")
    fun getMoviesByTitle(@Query("s") title: String): Call<SearchResult>
}
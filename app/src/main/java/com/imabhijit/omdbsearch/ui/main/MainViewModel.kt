package com.imabhijit.omdbsearch.ui.main

import androidx.lifecycle.ViewModel
import com.imabhijit.omdbsearch.model.SearchResult
import com.imabhijit.omdbsearch.service.MovieService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    fun getMoviesByTitle(title: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(MovieService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)
        val result = movieService.getMoviesByTitle(title)

        result.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if(response.isSuccessful) {
                    val result = response.body()
                    Picasso.get().load(result?.movies?.get(0)?.poster)

                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                println(t.message)
            }
        })
    }
}
package com.imabhijit.omdbsearch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imabhijit.omdbsearch.R
import com.imabhijit.omdbsearch.model.Movie
import com.imabhijit.omdbsearch.model.SearchResult
import com.imabhijit.omdbsearch.service.MovieAdapter
import com.imabhijit.omdbsearch.service.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        populateMoviesByTitle("see")
        return rootView
    }

    fun populateMoviesByTitle(title: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(R.string.BASE_URL.toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)
        val result = movieService.getMoviesByTitle(title)

        result.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if(response.isSuccessful) {
                    showData(response.body()!!.movies)
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                t.message?.let { Log.e("onGetFailure", it) }
            }
        })
    }

    fun showData(movies: List<Movie>){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MovieAdapter(movies)
        }
    }

}
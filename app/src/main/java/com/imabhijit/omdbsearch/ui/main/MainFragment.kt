package com.imabhijit.omdbsearch.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imabhijit.omdbsearch.MainActivity
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
import java.util.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val BASE_URL = "https://www.omdbapi.com/"
    }

    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var searchImage: ImageView
    lateinit var editText: EditText
    lateinit var movieService: MovieService
    lateinit var noResults: TextView
    lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        initializeComponents(rootView)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieService = retrofit.create(MovieService::class.java)

        val main = activity as MainActivity
        main.setSupportActionBar(toolbar)
        populateMoviesByTitle("Man")
        editText.addTextChangedListener(searchTextWatcher)
        return rootView
    }

    private fun initializeComponents(rootView: View) {
        recyclerView = rootView.findViewById(R.id.recyclerView)
        toolbar = rootView.findViewById(R.id.toolbar)
        searchImage = rootView.findViewById(R.id.searchImage)
        editText = rootView.findViewById(R.id.editText)
        noResults = rootView.findViewById(R.id.noResultText)
    }

    fun populateMoviesByTitle(title: String) {
        val result = movieService.getMoviesByTitle(title)
        result.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if(response.isSuccessful) {
//                    Log.d("retro", response.body().toString())
                    val itemList: List<Movie> = if (response.body()?.movies.isNullOrEmpty()) listOf() else response.body()?.movies!!
                    showData(itemList)
                } else {
                    Log.e("retro", response.raw().headers().toString())
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                t.message?.let { Log.e("onGetFailure", it) }
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun showData(movies: List<Movie>){
        noResults.text = if (movies.isEmpty()) getString(R.string.no_results_found) else String()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MovieAdapter(movies)
        }
    }

    var searchTextWatcher: TextWatcher = object : TextWatcher {
        var timer = Timer()
        override fun afterTextChanged(s: Editable) {
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    if(s.toString().length in 1..2) {
                        return
                    }
                    val searchTerm = if (s.toString().isEmpty()) "Man" else s.toString()
                    populateMoviesByTitle(searchTerm)
                }
            }, 200)
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            timer.cancel()
        }
    }
}
package com.imabhijit.omdbsearch.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imabhijit.omdbsearch.R
import com.imabhijit.omdbsearch.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.title.text = movies[position].title
        holder.year.text = movies[position].year
        movies[position].poster?.let {
            Picasso.get().load(it).into(holder.poster)
        }
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var year: TextView
        var poster: ImageView
        var watchButton: Button
        init {
            title = itemView.findViewById(R.id.title)
            year = itemView.findViewById(R.id.year)
            poster = itemView.findViewById(R.id.poster)
            watchButton = itemView.findViewById(R.id.watch_button)
        }
    }
}
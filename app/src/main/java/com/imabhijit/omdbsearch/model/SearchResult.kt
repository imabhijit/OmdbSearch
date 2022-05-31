package com.imabhijit.omdbsearch.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search")
    var movies: List<Movie>
)
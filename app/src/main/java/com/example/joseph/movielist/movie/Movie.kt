package com.example.joseph.movielist.movie

import com.google.gson.annotations.SerializedName

// Class used in the main Activity
class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String
)


package com.example.joseph.movielist.movie

import com.google.gson.annotations.SerializedName

// Class used in the DescriptionActivity
class MovieDescription(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("overview") val overview: String
) : java.io.Serializable

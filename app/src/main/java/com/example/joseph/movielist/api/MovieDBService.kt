package com.example.joseph.movielist.api

import com.example.joseph.movielist.movie.Movie
import com.example.joseph.movielist.movie.MovieDescription
import com.example.joseph.movielist.movie.MoviesResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieDBService {

    @GET("{variable}")
    fun getMoviesByID(
        @Path("variable") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<MovieDescription>

    @GET("now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): Observable<MoviesResponse>

    companion object {
        private const val API_URL = "https://api.themoviedb.org/3/movie/"
        fun create(): MovieDBService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .build()

            return retrofit.create(MovieDBService::class.java)
        }
    }
}
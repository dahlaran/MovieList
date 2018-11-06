package com.example.joseph.movielist.model

import com.example.joseph.movielist.BuildConfig
import com.example.joseph.movielist.api.MovieDBService
import com.example.joseph.movielist.presenter.MainPresenterInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainModel(val presenter: MainPresenterInterface?) : MainModelInterface {

    private val movieService by lazy {
        MovieDBService.create()
    }

    override fun getNowPlaying(page: Int) {
        val service = movieService.getNowPlaying(BuildConfig.MOVIE_DB_API_TOKEN, page, "fr")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> presenter?.nowPlayingSuccess(ArrayList(result.results)) },
                { error -> presenter?.nowPlayingError(error) }
            )
    }

    override fun getMovieById(id: Int) {

        val service = movieService.getMoviesByID(id, BuildConfig.MOVIE_DB_API_TOKEN, "fr")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> presenter?.movieByIDSuccess(result) },
                { error -> presenter?.movieByIDError(error) }
            )
    }
}

interface MainModelInterface {
    fun getNowPlaying(page: Int)
    fun getMovieById(id: Int)
}
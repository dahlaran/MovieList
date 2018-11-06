package com.example.joseph.movielist.presenter

import com.example.joseph.movielist.model.MainModel
import com.example.joseph.movielist.model.MainModelInterface
import com.example.joseph.movielist.movie.Movie
import com.example.joseph.movielist.movie.MovieDescription
import com.example.joseph.movielist.view.MainViewInterface

class MainPresenter(private var view: MainViewInterface?) :
    MainPresenterInterface {

    private val model: MainModelInterface = MainModel(this)

    override fun getNowPlaying(page: Int) {
        view?.showProgress()
        model.getNowPlaying(page)
    }

    override fun nowPlayingSuccess(movies: ArrayList<Movie>) {
        view?.nowPlayingSuccess(movies)
        view?.hideProgress()
    }

    override fun nowPlayingError(error: Throwable) {
        view?.nowPlayingFailed(error)
        view?.hideProgress()
    }

    override fun getMovieById(id: Int) {
        view?.showProgress()
        model.getMovieById(id)
    }

    override fun movieByIDSuccess(movie: MovieDescription) {
        view?.movieByIDSuccess(movie)
        view?.hideProgress()
    }

    override fun movieByIDError(error: Throwable) {
        view?.movieByIDError(error)
        view?.hideProgress()
    }
}

interface MainPresenterInterface {
    fun getNowPlaying(page: Int)
    fun nowPlayingSuccess(movies: ArrayList<Movie>)
    fun nowPlayingError(error: Throwable)
    fun getMovieById(id: Int)
    fun movieByIDSuccess(movie: MovieDescription)
    fun movieByIDError(error: Throwable)
}

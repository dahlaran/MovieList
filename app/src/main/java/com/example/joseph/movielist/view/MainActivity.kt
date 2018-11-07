package com.example.joseph.movielist.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
import android.widget.Toast
import com.example.joseph.movielist.R
import com.example.joseph.movielist.movie.Movie
import com.example.joseph.movielist.movie.MovieAdapter
import com.example.joseph.movielist.movie.MovieDescription
import com.example.joseph.movielist.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainViewInterface, OnClickListener {
    // For constants
    companion object {
        const val MOVIE_FOR_EXTRA: String = "movie_extra"
        private const val NUMBER_OF_MOVIE_BEFORE_OTHER_CALL: Int = 3
    }

    private lateinit var presenter: MainPresenter
    private lateinit var mainAdapter: MovieAdapter
    private lateinit var manager: LinearLayoutManager
    private var isScrolling: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        // Call API
        presenter = MainPresenter(this)
        presenter.getNowPlaying(mainAdapter.lastPage + 1)
    }

    private fun initRecyclerView() {
        manager = LinearLayoutManager(this@MainActivity)
        mainAdapter = MovieAdapter(this)

        MainRecyclerView.apply {
            layoutManager = manager
            adapter = mainAdapter
        }
        MainRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = manager.itemCount
                val currentItem = manager.childCount
                val firstVisibleItem = manager.findFirstVisibleItemPosition()

                // Check if the user scroll and he is near the end to the list and the progressbar is hide -> get other movies
                if (isScrolling && totalItemCount <= (currentItem + firstVisibleItem + NUMBER_OF_MOVIE_BEFORE_OTHER_CALL) && !MainProgressBar.isShown) {
                    isScrolling = false
                    presenter.getNowPlaying(mainAdapter.lastPage + 1)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })
    }

    override fun onItemClick(item: Int) {
        // Call API to get a better description of the movie
        presenter.getMovieById(item)
    }

    override fun showProgress() {
        MainProgressBar.visibility = VISIBLE
    }

    override fun hideProgress() {
        MainProgressBar.visibility = GONE
    }

    override fun nowPlayingSuccess(movies: ArrayList<Movie>) {
        onMovieResult(movies)
    }

    override fun nowPlayingFailed(error: Throwable) {
        whatToDoWhenErrorOccur(error)
    }

    private fun onMovieResult(movies: ArrayList<Movie>) {
        //sort the list to have latest movie on the top
        val sortedList = movies.sortedByDescending { it.release_date }

        mainAdapter.lastPage += 1
        for (movie in sortedList) {
            if (movie.poster_path != null && movie.poster_path.isNotEmpty()) {
                mainAdapter.movies.add(movie)
            }
        }

        mainAdapter.notifyDataSetChanged()
    }

    override fun movieByIDSuccess(movie: MovieDescription) {
        val intent = Intent(this, DescriptionActivity::class.java)

        intent.putExtra(MOVIE_FOR_EXTRA, movie)
        startActivity(intent)
    }

    override fun movieByIDError(error: Throwable) {
        whatToDoWhenErrorOccur(error)
    }

    private fun whatToDoWhenErrorOccur(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }
}

interface MainViewInterface {
    fun showProgress()
    fun hideProgress()
    fun nowPlayingSuccess(movies: ArrayList<Movie>)
    fun nowPlayingFailed(error: Throwable)
    fun movieByIDSuccess(movie: MovieDescription)
    fun movieByIDError(error: Throwable)
}
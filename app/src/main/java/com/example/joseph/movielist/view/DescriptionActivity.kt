package com.example.joseph.movielist.view

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.joseph.movielist.BuildConfig
import com.example.joseph.movielist.R
import com.example.joseph.movielist.movie.MovieDescription
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {

    // Used for const variables
    companion object {
        private const val RECOMMEND_MIN_MOVIE_FOR_GOOD: Int = 60
        private const val RECOMMEND_MAX_MOVIE_FOR_BAD: Int = 40
    }

    private lateinit var movieDesc: MovieDescription
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_description)
        movieDesc = intent.extras?.getSerializable(MainActivity.MOVIE_FOR_EXTRA) as MovieDescription

        if (movieDesc.title.isNotEmpty())
            initView()


        val actionBarTitle = resources.getString(R.string.activity_description_app_title)
        supportActionBar?.title = actionBarTitle
        actionBar?.title = actionBarTitle
    }

    private fun initView() {
        Picasso.get().load(BuildConfig.IMAGE_PATH + movieDesc.poster_path)
            .into(PhotoImageView)
        TitleTextView.text = movieDesc.title

        val recommend: Int = (movieDesc.vote_average * 10).toInt()
        RecommendTextView.text = resources.getString(
            R.string.activity_description_recommend_default_text,
            recommend
        )
        // Set color of the TextView depending of the average vote
        when {
            recommend >= RECOMMEND_MIN_MOVIE_FOR_GOOD -> RecommendTextView.setTextColor(
                ContextCompat.getColor(this, R.color.colorGood)
            )
            recommend <= RECOMMEND_MAX_MOVIE_FOR_BAD -> RecommendTextView.setTextColor(
                ContextCompat.getColor(this, R.color.colorBad)
            )
            else -> RecommendTextView.setTextColor(
                ContextCompat.getColor(this, R.color.colorMedium)
            )
        }

        DateTextView.text = movieDesc.release_date
        OverviewTextView.text = movieDesc.overview
    }
}
package com.example.joseph.movielist.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.joseph.movielist.BuildConfig.IMAGE_PATH
import com.example.joseph.movielist.R
import com.example.joseph.movielist.view.OnClickListener
import com.squareup.picasso.Picasso

// Adapter of the RecyclerView in the MainActivity
class MovieAdapter(private var listener: OnClickListener) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    // List of movies
    val movies: ArrayList<Movie> = ArrayList()
    // Save in memory the last asked API page
    var lastPage: Int = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = itemView.findViewById(R.id.PhotoImageView)
        val name: TextView = itemView.findViewById(R.id.TitleTextView)

        fun bind(item: Movie, listener: OnClickListener) {
            name.text = item.title
            itemView.setOnClickListener { listener.onItemClick(item.id) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.movie_row, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val currentMovie = movies[pos]
        holder.name.text = currentMovie.title
        if (currentMovie.poster_path.isNotEmpty()) {
            Picasso.get().load(IMAGE_PATH + currentMovie.poster_path)
                .into(holder.image)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.bind(movies[position], listener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
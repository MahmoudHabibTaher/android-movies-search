package com.bigo.movies.details.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bigo.movies.R
import com.bigo.movies.core.presentation.BaseFragment
import com.bigo.movies.core.presentation.gridLayoutManager
import com.bigo.movies.core.presentation.toCommaSeparated
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.entities.MovieParcelable
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

    private val args: MovieDetailsFragmentArgs by navArgs()

    private var movie: MovieParcelable? = null

    private val imagesAdapter by lazy {
        MovieImagesAdapter()
    }

    private val imagesObserver = Observer<List<MovieImage>> {
        imagesAdapter.setNewData(it)
    }

    private val moreImagesObserver = Observer<List<MovieImage>> {
        imagesAdapter.addData(it)
        imagesAdapter.loadMoreComplete()
    }

    private var moreImagesAvailableObserver = Observer<Boolean> {
        if (!it) {
            imagesAdapter.loadMoreEnd()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = args.movie

        observeLoadingVisible(movieDetailsViewModel, this)
        observeError(movieDetailsViewModel, this)
        observeImages(movieDetailsViewModel, this)

        movie?.let { movie ->
            movieDetailsViewModel.loadImages(movie.title)
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_movie_details

    override fun initView(view: View) {
        movie?.apply(::showMovie)

        images_recycler_view.apply {
            gridLayoutManager(2)
            adapter = imagesAdapter
        }

        imagesAdapter.setOnLoadMoreListener({
            movieDetailsViewModel.loadMoreImages()
        }, images_recycler_view)
    }

    private fun showMovie(movie: MovieParcelable) {
        movie_title_text_view.text = movie.title
        year_text_view.text = movie.year.toString()
        rating_text_view.text = movie.rating.toString()

        if (movie.cast.isNotEmpty()) {
            cast_label_text_view.visibility = View.VISIBLE
            cast_text_view.visibility = View.VISIBLE
            cast_text_view.text = movie.cast.toCommaSeparated()
        } else {
            cast_label_text_view.visibility = View.GONE
            cast_text_view.visibility = View.GONE
        }

        if (movie.genres.isNotEmpty()) {
            genres_label_text_view.visibility = View.VISIBLE
            genres_text_view.visibility = View.VISIBLE
            genres_text_view.text = movie.genres.toCommaSeparated()
        } else {
            genres_label_text_view.visibility = View.GONE
            genres_text_view.visibility = View.GONE
        }
    }

    private fun observeImages(viewModel: MovieDetailsViewModel, owner: LifecycleOwner) {
        viewModel.imagesLiveData.observe(owner, imagesObserver)

        viewModel.moreImagesLiveData.observe(owner, moreImagesObserver)

        viewModel.moreImagesAvailableLiveData.observe(owner, moreImagesAvailableObserver)
    }
}
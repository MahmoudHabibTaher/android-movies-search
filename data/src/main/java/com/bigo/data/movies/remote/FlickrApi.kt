package com.bigo.data.movies.remote

import com.bigo.data.movies.remote.entities.FlickrSearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("?method=flickr.photos.search&api_key=6322aa0cb1253b6820e5ced3c2afe8d1&format=json&content_type=1&sort=relevance&nojsoncallback=1")
    fun searchImages(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<FlickrSearchResult>

    companion object {
        fun buildUrl(farmId:String, serverId:String, imageId:String,
                     imageSecret:String) =
            "https://farm$farmId.staticflickr.com/$serverId/${imageId}_$imageSecret.jpg"

        const val BASE_URL = "https://www.flickr.com/services/rest/"
    }
}
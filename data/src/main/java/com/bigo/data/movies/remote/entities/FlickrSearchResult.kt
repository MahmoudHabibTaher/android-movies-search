package com.bigo.data.movies.remote.entities

import com.google.gson.annotations.SerializedName

class FlickrSearchResult(@SerializedName("photos") val photos: FlickrPhotos?)
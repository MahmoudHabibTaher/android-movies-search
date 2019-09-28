package com.bigo.data.core.retrofit

import retrofit2.Retrofit

object ServiceHelper {
    inline fun <reified T> createService(retrofit: Retrofit): T = retrofit.create(T::class.java)
}
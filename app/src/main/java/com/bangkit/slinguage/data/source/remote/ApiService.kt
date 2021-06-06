package com.bangkit.slinguage.data.source.remote

import retrofit2.Call
import retrofit2.http.*


interface ApiService {

//    @Headers("Content-Type: application/json")
    @POST("predict")
    fun predict(@Body body: ImagePost): Call<Predict>
}
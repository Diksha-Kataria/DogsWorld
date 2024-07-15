package com.example.dogsworld.api

import com.example.dogsworld.data.Dog
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/breeds/image/random")
    fun getDogImage(): Call<Dog>
}
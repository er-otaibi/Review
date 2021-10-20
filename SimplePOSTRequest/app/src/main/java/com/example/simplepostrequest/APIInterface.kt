package com.example.simplepostrequest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {

    @Headers("Content-Type: application/json")
    @POST("/custom-people/")
    fun addUser(@Body name: NamesItem ): Call<List<NamesItem>>
}
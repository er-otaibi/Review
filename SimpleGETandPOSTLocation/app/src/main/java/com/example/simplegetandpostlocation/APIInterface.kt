package com.example.simplegetandpostlocation

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {


    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUser(): Call<List<NamesItem>>

    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body name: NamesItem ): Call<List<NamesItem>>

}
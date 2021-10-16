package com.csobrino.pruebatecnica.api

import com.csobrino.pruebatecnica.data.Planet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search/")
    suspend fun getPlanets(
        @Query("search_query") searchQuery: String,
        @Query("number") number: Int
    ): Response<ArrayList<Planet>>
}
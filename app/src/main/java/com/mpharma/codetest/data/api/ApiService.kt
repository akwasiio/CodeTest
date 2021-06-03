package com.mpharma.codetest.data.api

import retrofit2.http.GET

interface ApiService {
    @GET("/5c3e15e63500006e003e9795")
    suspend fun getProducts(): ProductResponse
}
package com.aji.suitapps.utils.api

import com.aji.suitapps.utils.responses.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("users")
    suspend fun get(
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ) : Response<UserResponse?>
}
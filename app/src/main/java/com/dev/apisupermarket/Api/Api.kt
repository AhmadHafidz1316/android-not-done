package com.dev.apisupermarket.Api

import com.dev.apisupermarket.Request.LoginRequest
import com.dev.apisupermarket.Response.LoginResponse
import com.dev.apisupermarket.Response.ProductResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun login(
        @Body request: LoginRequest
    ) : Call<LoginResponse>

    @GET("products")
    fun getProducts(@Header("Authorization") data: String):Call <ProductResponse>
}
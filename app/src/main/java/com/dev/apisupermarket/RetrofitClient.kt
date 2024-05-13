package com.dev.apisupermarket

import com.dev.apisupermarket.Api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
       private val retrofit = Retrofit.Builder()
            .baseUrl("http://103.187.147.96/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun create(): Api {
            return retrofit.create(Api::class.java)
        }
    }
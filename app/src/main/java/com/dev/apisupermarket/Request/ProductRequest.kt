package com.dev.apisupermarket.Request

data class ProductRequest(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String
)
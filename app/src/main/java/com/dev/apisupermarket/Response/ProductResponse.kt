package com.dev.apisupermarket.Response

import com.dev.apisupermarket.Request.ProductRequest

data class ProductResponse(
    val data: List<ProductRequest>
)
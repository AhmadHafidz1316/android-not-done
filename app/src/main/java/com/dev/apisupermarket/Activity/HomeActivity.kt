package com.dev.apisupermarket.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.apisupermarket.Adapter.ProductAdapter
import com.dev.apisupermarket.R
import com.dev.apisupermarket.Request.ProductRequest
import com.dev.apisupermarket.Response.ProductResponse
import com.dev.apisupermarket.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: List<ProductRequest>
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferences = getSharedPreferences("com.dev.apisupermarket.Activity", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("TOKEN", "")
        if (!token.isNullOrEmpty()) {
            fetchProducts(token)
        } else {
            Toast.makeText(this, "Token not found", Toast.LENGTH_SHORT).show()
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu -> {
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchProducts(token: String) {
        val apiService = RetrofitClient.create()
        val call = apiService.getProducts("Bearer $token")

        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    productList = response.body()?.data ?: emptyList()
                    productAdapter = ProductAdapter(productList)
                    recyclerView.adapter = productAdapter
                } else {
                    Toast.makeText(this@HomeActivity, "Failed to fetch products", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

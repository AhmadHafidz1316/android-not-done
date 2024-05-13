package com.dev.apisupermarket.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.apisupermarket.R
import com.dev.apisupermarket.Request.LoginRequest
import com.dev.apisupermarket.Response.LoginResponse
import com.dev.apisupermarket.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var isPasswordVisible = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("com.dev.apisupermarket.Activity", Context.MODE_PRIVATE)

        val username: EditText = findViewById(R.id.username)
        val password : EditText = findViewById(R.id.password)
        val showPassword : AppCompatImageButton = findViewById(R.id.showPasswordButton)
        val login : Button = findViewById(R.id.loginButton)

        showPassword.setOnClickListener{
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible){
                password.transformationMethod = null
                showPassword.setImageResource(R.drawable.ic_visibility_off)
            } else {
                password.transformationMethod = android.text.method.PasswordTransformationMethod()
                showPassword.setImageResource(R.drawable.ic_visibility_off)
            }

        }

        login.setOnClickListener{
            val username = username.text.toString()
            val password = password.text.toString()

            val loginRequest = LoginRequest(username, password)

            RetrofitClient.create().login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful){
                        val loginResponse = response.body()
                        val token = loginResponse?.data ?: ""
                        if (token.isNotEmpty()) {
                            saveToken(token)
                            Toast.makeText(this@MainActivity,"Login Berhasil", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@MainActivity, "Token tidak ditemukan dalam respons", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Login Gagal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
        }



    }

    override fun onClick(v: View?) {
       if (v != null){
           when(v.id){
               R.id.regisButton -> {
                   val pindah = Intent(this,RegisterActivity::class.java)
                   startActivity(pindah)
               }
           }
       }
    }

    private fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("TOKEN", token)
        editor.apply()
    }


}
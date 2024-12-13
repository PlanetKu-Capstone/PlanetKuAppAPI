package com.dicoding.planetkuapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.ActivityRegisterBinding
import com.dicoding.planetkuapp.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Data Model
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String,
    val token: String? = null
)

// API Service
interface RegisterApiService {
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse
}

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var apiService: RegisterApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Retrofit
        apiService = Retrofit.Builder()
            .baseUrl("https://main-api-83958552057.asia-southeast2.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegisterApiService::class.java)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(name, email, password)) {
                performRegister(name, email, password)
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.etName.error = getString(R.string.name_required)
            isValid = false
        } else {
            binding.etName.error = null
        }

        if (email.isEmpty()) {
            binding.etEmail.error = getString(R.string.email_required)
            isValid = false
        } else {
            binding.etEmail.error = null
        }

        if (password.isEmpty()) {
            binding.etPassword.error = getString(R.string.password_required)
            isValid = false
        } else {
            binding.etPassword.error = null
        }

        return isValid
    }

    private fun performRegister(name: String, email: String, password: String) {
        lifecycleScope.launch {
            try {
                val request = RegisterRequest(name, email, password)
                val response = withContext(Dispatchers.IO) {
                    apiService.registerUser(request)
                }

                Toast.makeText(
                    this@RegisterActivity,
                    getString(R.string.register_success),
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    getString(R.string.register_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

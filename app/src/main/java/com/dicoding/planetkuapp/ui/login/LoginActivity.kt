package com.dicoding.planetkuapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.planetkuapp.MainActivity
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.ActivityLoginBinding
import com.dicoding.planetkuapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.error_email_required)
            return false
        } else {
            binding.emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.error_password_required)
            return false
        } else {
            binding.passwordInputLayout.error = null
        }

        return true
    }

    private fun performLogin(email: String, password: String) {
        if (email == "user@example.com" && password == "password") {
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
        }
    }
}
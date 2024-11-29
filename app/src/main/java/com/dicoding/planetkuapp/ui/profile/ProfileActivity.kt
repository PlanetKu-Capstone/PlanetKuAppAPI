package com.dicoding.planetkuapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action for Save Profile button
        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val address = binding.etAddress.text.toString()
            val phone = binding.etPhone.text.toString()

            // Save or handle the profile data (here just showing a toast for simplicity)
            if (name.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty()) {
                // Simulate saving the profile data
                Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show()

                // Here you could save the data to a database or preferences
                // For example, SharedPreferences or Room database
            } else {
                // Handle validation if fields are empty
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
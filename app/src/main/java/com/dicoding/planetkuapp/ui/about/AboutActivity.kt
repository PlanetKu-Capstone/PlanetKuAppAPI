package com.dicoding.planetkuapp.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAboutAppContent.text = getString(R.string.about_app_description)
    }
}
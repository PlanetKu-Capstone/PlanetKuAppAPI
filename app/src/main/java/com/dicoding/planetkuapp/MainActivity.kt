package com.dicoding.planetkuapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.planetkuapp.databinding.ActivityMainBinding
import com.dicoding.planetkuapp.ui.ArticleFragment
import com.dicoding.planetkuapp.ui.MapsFragment
import com.dicoding.planetkuapp.ui.more.MoreFragment
import com.dicoding.planetkuapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> loadFragment(HomeFragment())
                R.id.menu_map -> loadFragment(MapsFragment())
                R.id.menu_article -> loadFragment(ArticleFragment())
                R.id.menu_more -> loadFragment(MoreFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}


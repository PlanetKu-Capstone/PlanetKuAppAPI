package com.dicoding.planetkuapp.ui.settings

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.FragmentSettingsBinding
import com.dicoding.planetkuapp.ui.AboutAppFragment
import com.dicoding.planetkuapp.ui.priceprediction.PricePredictionFragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            // Tombol Logout
            btnLogout.setOnClickListener {
                Toast.makeText(requireContext(), "Logout Clicked", Toast.LENGTH_SHORT).show()
            }

            // Tombol Tentang Aplikasi
            btnAboutApp.setOnClickListener {
                startActivity(Intent(requireContext(), AboutAppFragment::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


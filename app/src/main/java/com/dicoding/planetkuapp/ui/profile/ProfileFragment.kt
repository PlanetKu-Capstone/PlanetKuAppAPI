package com.dicoding.planetkuapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menginisialisasi ViewBinding
        _binding = FragmentProfileBinding.bind(view)

        // Menampilkan data pengguna (bisa dari API atau database)
        binding.tvUsername.text = "John Doe"
        binding.ivProfile.setImageResource(R.drawable.ic_profile)

        // Edit profile button
        binding.btnEditProfile.setOnClickListener {
            // Menavigasi ke halaman edit profil
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        // Edit Nama
        binding.etName.setText("John Doe")
        // Edit Email
        binding.etEmail.setText("johndoe@example.com")
        // Edit Address
        binding.etAddress.setText("123 Green St, Earth City")
        // Edit Phone
        binding.etPhone.setText("+1234567890")

        // Kamu bisa menambahkan logika untuk menyimya menggunakan SharedPreferences, database, atau API
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Menghancurkan binding saat view dihancurkan untuk mencegah memory leak
        _binding = null
    }
}

package com.dicoding.planetkuapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProfileBinding.bind(view)

        binding.etName.setText("John Doe")
        binding.etEmail.setText("johndoe@example.com")
        binding.etAddress.setText("123 Green St, Earth City")
        binding.etPhone.setText("+1234567890")

        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val address = binding.etAddress.text.toString()
            val phone = binding.etPhone.text.toString()

            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua field!", Toast.LENGTH_SHORT).show()
            } else {
                saveProfileData(name, email, address, phone)

                Toast.makeText(requireContext(), "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProfileData(name: String, email: String, address: String, phone: String) {
        val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("name", name)
            .putString("email", email)
            .putString("address", address)
            .putString("phone", phone)
            .apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

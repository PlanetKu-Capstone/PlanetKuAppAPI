package com.dicoding.planetkuapp.ui.classification

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.FragmentWasteClassificationBinding

class WasteClassificationFragment : Fragment(R.layout.fragment_waste_classification) {

    private var _binding: FragmentWasteClassificationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWasteClassificationBinding.bind(view)

        val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photo: Bitmap = result.data?.extras?.get("data") as Bitmap
                binding.ivPreview.setImageBitmap(photo)
            } else {
                Toast.makeText(requireContext(), "Gagal mengambil gambar dari kamera", Toast.LENGTH_SHORT).show()
            }
        }

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.ivPreview.setImageURI(uri)
            } else {
                Toast.makeText(requireContext(), "Gagal memilih gambar dari galeri", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCapture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }

        binding.btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
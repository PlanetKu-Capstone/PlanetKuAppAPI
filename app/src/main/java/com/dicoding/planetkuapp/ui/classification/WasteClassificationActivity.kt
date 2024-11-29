package com.dicoding.planetkuapp.ui.classification

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.planetkuapp.databinding.ActivityWasteClassificationBinding
import com.dicoding.planetkuapp.ui.priceprediction.PricePredictionActivity


class WasteClassificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWasteClassificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWasteClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photo: Bitmap = result.data?.extras?.get("data") as Bitmap
                binding.ivPreview.setImageBitmap(photo)
            } else {
                Toast.makeText(this, "Gagal mengambil gambar dari kamera", Toast.LENGTH_SHORT).show()
            }
        }

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.ivPreview.setImageURI(uri)
            } else {
                Toast.makeText(this, "Gagal memilih gambar dari galeri", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCapture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }

        binding.btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnPredict.setOnClickListener {
            val classifiedWaste = "Plastik" // Contoh hasil klasifikasi
            navigateToPricePrediction(classifiedWaste)
        }

    }

    private fun navigateToPricePrediction(classifiedWaste: String) {
        val intent = Intent(this, PricePredictionActivity::class.java)
        intent.putExtra("CLASSIFIED_WASTE", classifiedWaste)
        startActivity(intent)
    }

}

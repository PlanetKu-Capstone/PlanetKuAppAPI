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
import com.dicoding.planetkuapp.network.ApiClient
import com.dicoding.planetkuapp.network.WasteResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class WasteClassificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWasteClassificationBinding
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWasteClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Launchers
        val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photo: Bitmap = result.data?.extras?.get("data") as Bitmap
                binding.ivPreview.setImageBitmap(photo)
                selectedImageUri = saveBitmapToUri(photo)
            } else {
                showToast("Gagal mengambil gambar dari kamera")
            }
        }

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.ivPreview.setImageURI(uri)
                selectedImageUri = uri
            } else {
                showToast("Gagal memilih gambar dari galeri")
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
            selectedImageUri?.let { uri ->
                uploadImage(uri)
            } ?: showToast("Pilih gambar terlebih dahulu")
        }
    }

    private fun uploadImage(uri: Uri) {
        val file = File(getRealPathFromURI(uri)!!)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        ApiClient.apiService.classifyWaste(body).enqueue(object : Callback<WasteResponse> {
            override fun onResponse(call: Call<WasteResponse>, response: Response<WasteResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        showToast("Prediksi: ${it.prediksi}\nKepercayaan: ${it.confidence}")
                        navigateToPricePrediction(it.prediksi)
                    }
                } else {
                    showToast("Gagal memprediksi gambar")
                }
            }

            override fun onFailure(call: Call<WasteResponse>, t: Throwable) {
                showToast("Kesalahan jaringan: ${t.message}")
            }
        })
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, filePathColumn, null, null, null)?.apply {
            moveToFirst()
            val columnIndex = getColumnIndex(filePathColumn[0])
            val path = getString(columnIndex)
            close()
            return path
        }
        return null
    }

    private fun saveBitmapToUri(bitmap: Bitmap): Uri {
        val file = File(cacheDir, "temp_image.jpg")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return Uri.fromFile(file)
    }

    private fun navigateToPricePrediction(classifiedWaste: String) {
        val intent = Intent(this, PricePredictionActivity::class.java)
        intent.putExtra("CLASSIFIED_WASTE", classifiedWaste)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

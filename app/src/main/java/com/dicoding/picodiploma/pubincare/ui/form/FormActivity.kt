package com.dicoding.picodiploma.pubincare.ui.form

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import com.dicoding.picodiploma.pubincare.R
import com.dicoding.picodiploma.pubincare.databinding.ActivityFormBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val REQUEST_CODE = 72
class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private lateinit var title: String

    private var imageUri: Uri? = null
    private val storageReference = FirebaseStorage.getInstance().getReference("uploads")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setImageViewHome()
        initAction()
    }

    private fun uploadImage(title: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            imageUri?.let { uri ->
                storageReference.child(title).putFile(uri)
                    .addOnProgressListener {
                        val progress: Int = ((100 * it.bytesTransferred) / it.totalByteCount).toInt()
                        binding.progressBarLoadingIndicator.progress = progress
                        val indicatorText = "Loading.. $progress%"
                        binding.textViewIndicatorLoading.text = indicatorText
                    }.await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FormActivity, "Success Upload!", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@FormActivity, e.message, Toast.LENGTH_SHORT).show()
            }
    }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            data?.data?.let {
                imageUri = it
                binding.imageViewHome.load(imageUri) {
                    crossfade(true)
                    crossfade(500)
                    transformations(RoundedCornersTransformation(15f))
                }
            }
        }
    }

    private fun initAction() {
        binding.buttonSelectImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE)
            }
        }

        binding.buttonUploadImage.setOnClickListener {
            title = binding.editTextTitle.text.toString().trim()
            if (imageUri != null) {
                if (title.isBlank() || title.isEmpty()) {
                    binding.inputTextTitle.error = "*Required"
                } else {
                    binding.progressBarLoadingIndicator.isIndeterminate = false
                    binding.progressBarLoadingIndicator.visibility = View.VISIBLE
                    binding.textViewIndicatorLoading.visibility = View.VISIBLE
                    binding.inputTextTitle.error = null
                    uploadImage(title)
                }
            } else {
                Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setImageViewHome() {
        binding.imageViewHome.load(ContextCompat.getDrawable(this, R.drawable.shape)) {
            crossfade(true)
            crossfade(500)
            transformations(RoundedCornersTransformation(15f))
        }
    }
}
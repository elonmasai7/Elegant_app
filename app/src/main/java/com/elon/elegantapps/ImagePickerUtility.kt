package com.elon.elegantapps

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImagePickerUtility {

    private var cameraImageUri: Uri? = null
    private var onImagePicked: ((Uri?) -> Unit)? = null

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    fun initialize(activity: AppCompatActivity) {
        cameraLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                onImagePicked?.invoke(cameraImageUri)
            }
        }

        galleryLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                onImagePicked?.invoke(result.data?.data)
            }
        }
    }

    fun pickImage(activity: AppCompatActivity, callback: (Uri?) -> Unit) {
        onImagePicked = callback

        val options = arrayOf("Take Photo", "Choose from Gallery")
        android.app.AlertDialog.Builder(activity)
            .setTitle("Select Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera(activity)
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera(activity: AppCompatActivity) {
        if (!hasCameraPermissions(activity)) {
            requestCameraAndStoragePermissions(activity)
            return
        }

        createImageFile(activity)?.let { file ->
            cameraImageUri = FileProvider.getUriForFile(activity, "${activity.packageName}.provider", file)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri)
            }
            cameraLauncher.launch(intent)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
        galleryLauncher.launch(intent)
    }

    fun requestCameraAndStoragePermissions(activity: Activity) {
        if (!hasCameraPermissions(activity)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }

    private fun hasCameraPermissions(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun createImageFile(activity: AppCompatActivity): File? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File.createTempFile("IMG_${timeStamp}_", ".jpg", storageDir)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

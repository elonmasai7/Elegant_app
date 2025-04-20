package com.elon.elegantapps

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EmployeeFormActivity : AppCompatActivity() {

    private lateinit var imgEmployeePhoto: ImageView
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etDateOfBirth: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var etDateHired: EditText
    private lateinit var etContractExpiration: EditText
    private lateinit var etPosition: EditText
    private lateinit var etPositionStartDate: EditText
    private lateinit var etManagerName: EditText
    private lateinit var etEmergencyContact: EditText
    private lateinit var btnSubmit: Button

    private var cameraImageUri: Uri? = null
    private val imagePickerUtility = ImagePickerUtility()

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            cameraImageUri?.let { loadImage(it) }
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { loadImage(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_form)

        initializeViews()
        setupImageUpload()
        setupDatePickers()
        setupGenderSpinner()
        checkPermissions()
        setupSubmitButton()
    }

    private fun initializeViews() {
        imgEmployeePhoto = findViewById(R.id.imgEmployeePhoto)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etDateOfBirth = findViewById(R.id.etDateOfBirth)
        spinnerGender = findViewById(R.id.spinnerGender)
        etDateHired = findViewById(R.id.etDateHired)
        etContractExpiration = findViewById(R.id.etContractExpiration)
        etPosition = findViewById(R.id.etPosition)
        etPositionStartDate = findViewById(R.id.etPositionStartDate)
        etManagerName = findViewById(R.id.etManagerName)
        etEmergencyContact = findViewById(R.id.etEmergencyContact)
        btnSubmit = findViewById(R.id.btnSubmit)
    }

    private fun setupImageUpload() {
        imgEmployeePhoto.setOnClickListener { openImagePicker() }
        imagePickerUtility.initialize(this)
    }

    private fun setupDatePickers() {
        listOf(etDateOfBirth, etDateHired, etContractExpiration, etPositionStartDate).forEach { editText ->
            editText.setOnClickListener { showDatePicker(editText) }
        }
    }

    private fun setupGenderSpinner() {
        val genders = listOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }

    private fun setupSubmitButton() {
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                saveEmployeeData()
            }
        }
    }

    private fun openImagePicker() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        AlertDialog.Builder(this)
            .setTitle("Select Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        imagePickerUtility.createImageFile(this)?.let { file ->
            cameraImageUri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", file)
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

    private fun loadImage(uri: Uri) {
        Glide.with(this).load(uri).centerCrop().into(imgEmployeePhoto)
    }

    private fun checkPermissions() {
        imagePickerUtility.requestCameraAndStoragePermissions(this)
    }

    private fun validateForm(): Boolean {
        var isValid = true
        if (etFirstName.text.isNullOrEmpty()) {
            etFirstName.error = "First name required"
            isValid = false
        }
        if (etLastName.text.isNullOrEmpty()) {
            etLastName.error = "Last name required"
            isValid = false
        }
        if (etDateOfBirth.text.isNullOrEmpty()) {
            etDateOfBirth.error = "Date of birth required"
            isValid = false
        }
        if (spinnerGender.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }

    private fun saveEmployeeData() {
        Toast.makeText(this, "Employee data saved!", Toast.LENGTH_SHORT).show()
        clearForm()
    }

    private fun clearForm() {
        listOf(etFirstName, etLastName, etDateOfBirth, etDateHired, etContractExpiration, etPosition, etPositionStartDate, etManagerName, etEmergencyContact).forEach { it.text.clear() }
        spinnerGender.setSelection(0)
        imgEmployeePhoto.setImageResource(R.drawable.ic_person_placeholder)
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val formattedDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
            editText.setText(formattedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}

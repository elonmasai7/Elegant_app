package com.elon.elegantapps

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShowAlert: Button = findViewById(R.id.btnShowAlert)

        btnShowAlert.setOnClickListener {
            showAlert()
        }
    }

    private fun showAlert() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Success")
            .setMessage("Saved file")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
}

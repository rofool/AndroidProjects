package com.example.firsttry

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var inputEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)

        val openSecondButton = findViewById<Button>(R.id.openSecondButton)
        val callButton = findViewById<Button>(R.id.callButton)
        val shareButton = findViewById<Button>(R.id.shareButton)

        // Явный Intent — открыть вторую Activity
        openSecondButton.setOnClickListener {
            val text = inputEditText.text.toString().trim()

            if (text.isEmpty()) {
                inputEditText.error = "Введите текст"
                return@setOnClickListener
            }

            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra(EXTRA_TEXT, text)

            startActivity(intent)
        }

        // Неявный Intent — открыть приложение для звонков
        callButton.setOnClickListener {
            val phone = inputEditText.text.toString().trim()

            if (!isValidPhone(phone)) {
                inputEditText.error = "Введите корректный номер телефона"
                return@setOnClickListener
            }

            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.fromParts("tel", phone, null)
            )

            startActivity(intent)
        }

        // Системный Intent — поделиться текстом
        shareButton.setOnClickListener {
            val text = inputEditText.text.toString().trim()

            if (text.isEmpty()) {
                Toast.makeText(
                    this,
                    "Сначала введите текст",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }

            val chooser = Intent.createChooser(
                sendIntent,
                "Поделиться через..."
            )

            startActivity(chooser)
        }
    }

    private fun isValidPhone(phone: String): Boolean {
        val phoneRegex = Regex("^\\+?[0-9][0-9()\\-\\s]{4,}$")
        return phoneRegex.matches(phone)
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}
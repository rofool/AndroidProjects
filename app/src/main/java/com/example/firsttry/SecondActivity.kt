package com.example.firsttry

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second)

        val receivedTextView = findViewById<TextView>(R.id.receivedTextView)

        val receivedText = intent.getStringExtra(MainActivity.EXTRA_TEXT)

        if (receivedText.isNullOrBlank()) {
            receivedTextView.text = "Текст не был передан"
        } else {
            receivedTextView.text = receivedText
        }
    }
}
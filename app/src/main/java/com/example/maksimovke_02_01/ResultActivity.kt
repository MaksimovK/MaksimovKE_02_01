package com.example.maksimovke_02_01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        nameTextView = findViewById(R.id.figure_name)
        resultTextView = findViewById(R.id.figure_result)
        registerButton = findViewById(R.id.button_to_register)

        registerButton.setOnClickListener{ goToRegisterActivity() }

        val figureName = intent.getStringExtra("FIGURE_NAME")
        val figureResult = intent.getStringExtra("RESULT")

        nameTextView.text = figureName
        resultTextView.text = figureResult

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun goToRegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
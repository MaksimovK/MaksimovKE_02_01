package com.example.maksimovke_02_01

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        loginEditText = findViewById(R.id.edit_text_login)
        passwordEditText = findViewById(R.id.edit_text_password)
        registerButton = findViewById(R.id.button_register)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        registerButton.setOnClickListener{ onRegister() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun onRegister() {
        val login = loginEditText.text.toString()
        val password = passwordEditText.text.toString()

        if(TextUtils.isEmpty(login) || TextUtils.isEmpty(password)) {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Ошибка")
            alert.setMessage("Заполните поля")
            alert.setPositiveButton("ОК") {
                alert, id ->
            }
            alert.show()
            return;
        }

        val storedLogin = sharedPreferences.getString("LOGIN", null);
        val storedPassword = sharedPreferences.getString("PASSWORD", null);

        if(storedLogin == null && storedPassword == null) {
            with(sharedPreferences.edit()) {
                putString("LOGIN", login)
                putString("PASSWORD", password)
                apply()
            }
            Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CalculateActivity::class.java))
        } else {
            if(storedLogin == login && storedPassword == password) {
                Toast.makeText(this, "Вход успешен", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, CalculateActivity::class.java))
            } else {
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Ошибка")
                alert.setMessage("Неверные данные")
                alert.setPositiveButton("ОК") {
                        alert, id ->
                }
                alert.show()
                return;
            }
        }


    }
}
package com.example.maksimovke_02_01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.PI

class CalculateActivity : AppCompatActivity() {
    private lateinit var figureImageView: ImageView
    private lateinit var figureSpinner: Spinner
    private lateinit var calculateButton: Button
    private lateinit var dataEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculate)

        figureSpinner = findViewById(R.id.spinner_figure)
        figureImageView = findViewById(R.id.image_view)
        calculateButton = findViewById(R.id.button_calculate)
        dataEditText = findViewById(R.id.edit_text_data)

        val adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        figureSpinner.adapter = adapter

        figureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedFigure = parent.getItemAtPosition(position).toString()
                updateImageView(selectedFigure)
                updateHint(selectedFigure)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        calculateButton.setOnClickListener{ onCalculate() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateHint(figure: String) {
        when(figure) {
            "Треугольник" -> dataEditText.hint = "Введите два значения через пробел"
            "Круг" -> dataEditText.hint = "Введите значение"
        }
    }

    private fun updateImageView(figure: String) {
        when(figure) {
            "Треугольник" -> figureImageView.setImageResource(R.drawable.triangle)
            "Круг" -> figureImageView.setImageResource(R.drawable.circle)
        }
    }


    private fun onCalculate() {
        val data = dataEditText.text.toString()

        if(data.isEmpty()) {
            Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show()
            return
        }

        when(val figure = figureSpinner.selectedItem.toString()) {
            "Треугольник" -> {
                val values = data.split(" ")
                if(values.size != 2) {
                    Toast.makeText(this, "Введите два значения через пробел", Toast.LENGTH_SHORT).show()
                    return
                }

                val perimeter = 2 * values[0].toDouble() + values[1].toDouble()
                navigateToThirdActivity(figure, perimeter.toString())
            }
            "Круг" -> {
                val values = data.split(" ")
                if(values.size != 1) {
                    Toast.makeText(this, "Введите одно число", Toast.LENGTH_SHORT).show()
                    return
                }

                val area = values[0].toDouble() / (2 * PI)
                navigateToThirdActivity(figure, area.toString())
            }
        }
    }

    private fun navigateToThirdActivity(name: String, result: String) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("FIGURE_NAME", name)
            putExtra("RESULT", result)
        }
        startActivity(intent)
    }
}
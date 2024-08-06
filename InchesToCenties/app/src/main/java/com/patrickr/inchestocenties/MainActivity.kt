package com.patrickr.inchestocenties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

	private lateinit var enterInches: EditText
	private lateinit var convertButton: Button
	private lateinit var textViewCent: TextView
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		enterInches = findViewById(R.id.inchesInput)
		convertButton = findViewById(R.id.conversionButton)
		textViewCent = findViewById(R.id.centiOutput)

		addButtonListener()
	}

	private fun addButtonListener() {
		convertButton.setOnClickListener {
			if (!enterInches.text.toString().isEmpty()) {
				val result = enterInches.text.toString().toDouble() * MainActivity.inchRation
				textViewCent.text = result.toString() + "cm"
			} else {
				textViewCent.text = getString(R.string.input_error)
			}
		}
	}

	companion object {
		val inchRation = 2.54
	}

}
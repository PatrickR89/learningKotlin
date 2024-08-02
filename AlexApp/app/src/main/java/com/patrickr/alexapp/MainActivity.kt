package com.patrickr.alexapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
	private lateinit var button: Button
	private lateinit var textView: TextView
	private lateinit var editText: EditText

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		button = findViewById(R.id.showButton)
		textView = findViewById(R.id.textViewOne)
		editText = findViewById(R.id.editText)

		button.setOnClickListener {
			if (editText.text.isEmpty()) {
				textView.text = "Please enter your name."
				textView.visibility = View.VISIBLE
				return@setOnClickListener
			}
			val input = editText.text
			textView.visibility = View.VISIBLE
			textView.text = input
		}


	}

//	fun showMessage(view: View) {
//		textView.visibility = View.VISIBLE
//	}
}
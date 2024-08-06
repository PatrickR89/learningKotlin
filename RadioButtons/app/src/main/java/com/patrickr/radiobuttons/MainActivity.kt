package com.patrickr.radiobuttons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
	private lateinit var radioGroup: RadioGroup
	private lateinit var radioButton: RadioButton

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		radioGroup = findViewById(R.id.radioGroup)

		radioGroup.setOnCheckedChangeListener { radioGroup, id ->
			radioButton = findViewById(id)

			when(radioButton.id) {
				R.id.radioButton1 -> {
					Toast.makeText(this@MainActivity, "Glad you like it.", Toast.LENGTH_SHORT).show()
				}
					R.id.radioButton2 -> {
						Toast.makeText(this@MainActivity, "Sad you don't..", Toast.LENGTH_SHORT).show()
					}
				R.id.radioButton3 -> {
					Toast.makeText(this@MainActivity, "It's not that bad..", Toast.LENGTH_SHORT).show()
				}
			}
		}
	}
}
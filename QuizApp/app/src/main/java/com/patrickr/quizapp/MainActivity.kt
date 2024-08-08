package com.patrickr.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.patrickr.quizapp.ui.QuestionsActivity
import com.patrickr.quizapp.utils.KeyConstants

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val startButton: Button = findViewById(R.id.button_start)
		val editTextName: EditText = findViewById(R.id.name)

		startButton.setOnClickListener {
			if(!editTextName.text.isEmpty()) {
				Intent(this@MainActivity, QuestionsActivity::class.java).also {
					it.putExtra(KeyConstants.userName.key, editTextName.text.toString())
					startActivity(it)
					finish()
				}
			} else {
				Toast.makeText(this@MainActivity, "Please enter your name", Toast.LENGTH_LONG).show()
			}
		}
	}
}
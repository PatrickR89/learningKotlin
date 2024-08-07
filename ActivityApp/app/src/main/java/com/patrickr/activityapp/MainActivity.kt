package com.patrickr.activityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
	private lateinit var button: Button
	private lateinit var textViewData: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
			if (it.resultCode == Constants.RESULT_CODE) {
				val message = it.data.
			}
		}
		textViewData = findViewById(R.id.textViewData)
		button = findViewById(R.id.button)
		button.setOnClickListener {
			val intent = Intent(this@MainActivity, SecondActivity:: class.java)

			intent.putExtra(Constants.INTENT_MESSAGE_KEY, "Hello!")
			startActivity(intent)
		}


	}

	override fun onStart() {
		super.onStart()
		println("onStart()")
	}

	override fun onResume() {
		super.onResume()
		println("onResume()")
	}

	override fun onStop() {
		super.onStop()
		println("onStop()")
	}

	override fun onPause() {
		super.onPause()
		println("onPause()")
	}

	override fun onDestroy() {
		super.onDestroy()
		println("onDestroy()")
	}
}
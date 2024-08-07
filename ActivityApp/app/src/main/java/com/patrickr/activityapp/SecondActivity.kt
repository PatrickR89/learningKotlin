package com.patrickr.activityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class SecondActivity : AppCompatActivity() {
	private lateinit var backButton: Button
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_second)

		val data = intent.extras
		data?.let {
			val message = it.getString(Constants.INTENT_MESSAGE_KEY)
			Toast.makeText(this@SecondActivity, message, Toast.LENGTH_LONG).show()
		}

		backButton = findViewById(R.id.button2)
		backButton.setOnClickListener {
			val intent = intent
			intent.putExtra(Constants.MESSAGE_RESPONSE, "Hi back!")
			setResult(Constants.RESULT_CODE, intent)
			finish()
		}
	}
}
package com.patrickr.sharedprefs

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
	private lateinit var editTextMessage: AppCompatEditText
	private lateinit var saveButton: AppCompatButton
	private lateinit var textView: TextView
	private lateinit var sharedPref: SharedPreferences


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		getUI()
		getPreferences()
	}

	private fun getUI() {
		editTextMessage = findViewById(R.id.edit_text_message)
		textView = findViewById(R.id.text_view_message)
		saveButton = findViewById(R.id.save_button)
		sharedPref = getSharedPreferences(MainActivity.PREF_NAME, 0)
		saveButton.setOnClickListener {

			val editor = sharedPref.edit()

			editor.putString(MainActivity.MSG_KEY, editTextMessage.text.toString())
			editor.commit() // use apply() for background storage
		}
	}

	fun getPreferences() {
		val preferences = getSharedPreferences(MainActivity.PREF_NAME, 0)
		if (preferences.contains(MainActivity.MSG_KEY)) {
		}

		preferences.getString(MainActivity.MSG_KEY, null)?.let {
			textView.text = "Message:" + it
		}
	}

	companion object {
		private const val PREF_NAME = "my_app"
		private const val MSG_KEY = "message"
	}
}
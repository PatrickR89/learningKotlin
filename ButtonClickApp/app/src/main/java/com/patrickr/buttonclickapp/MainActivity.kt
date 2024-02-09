package com.patrickr.buttonclickapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

private val TAG = "MainActivity"
private const val TEXT_CONTENTS = "TextContent"

class MainActivity : AppCompatActivity() {
	private var textView: TextView? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		Log.d(TAG, "onCreate: called")
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val editText: EditText = findViewById<EditText>(R.id.editText)
		editText.text.clear()
		val button: Button = findViewById<Button>(R.id.button)
		textView = findViewById<TextView>(R.id.textView)
		textView?.text = ""
		textView?.movementMethod = ScrollingMovementMethod()

		button.setOnClickListener(object : View.OnClickListener {

			override fun onClick(v: View?) {
				Log.d(TAG, "onClick: called")
				textView?.append(editText.text)
				textView?.append("\n")
				editText.text.clear()
			}
		})
	}

	override fun onStart() {
		Log.d(TAG, "onStart: called")
		super.onStart()
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		Log.d(TAG, "onClick: called")
		super.onRestoreInstanceState(savedInstanceState)
		val savedString = savedInstanceState?.getString(TEXT_CONTENTS, "")
		textView?.text = savedString
	}

	override fun onSaveInstanceState(outState: Bundle) {
		Log.d(TAG, "onSaveInstanceState: called")
		super.onSaveInstanceState(outState)
		outState?.putString(TEXT_CONTENTS, textView?.text.toString())
	}

	override fun onResume() {
		Log.d(TAG, "onResume: called")
		super.onResume()
	}

	override fun onPause() {
		Log.d(TAG, "onPause: called")
		super.onPause()
	}

	override fun onStop() {
		Log.d(TAG, "onStop: called")
		super.onStop()
	}

	override fun onDestroy() {
		Log.d(TAG, "onDestroy: called")
		super.onDestroy()
	}
}
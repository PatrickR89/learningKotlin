package com.patrickr.applicationthemes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val button = findViewById<Button>(R.id.nextActivityButton)

		button.setOnClickListener {
			val intent = Intent(this@MainActivity, SecondActivity::class.java)
			startActivity(intent)
		}

		val toolBarBtn: Button = findViewById(R.id.toolbarBtn)

		toolBarBtn.setOnClickListener {
			val intent = Intent(this@MainActivity, ToolbarActivity::class.java)
			startActivity(intent)
		}
	}
}
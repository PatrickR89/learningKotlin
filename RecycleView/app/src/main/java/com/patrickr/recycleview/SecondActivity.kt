package com.patrickr.recycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.patrickr.recycleview.Utils.Constants
import org.w3c.dom.Text

class SecondActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_second)

		val extras = intent.extras
		val titleView: TextView = findViewById(R.id.titleView)
		val descriptionView: TextView = findViewById(R.id.descriptionView)

		val title = extras?.getString(Constants.title.key)
		val description = extras?.getString(Constants.description.key)

		title?.let {
			titleView.text = it
		}

		description?.let {
			descriptionView.text = it
		}
	}
}
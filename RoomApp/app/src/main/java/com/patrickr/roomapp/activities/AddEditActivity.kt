package com.patrickr.roomapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.patrickr.roomapp.CodeConstants
import com.patrickr.roomapp.KeyConstants
import com.patrickr.roomapp.R

class AddEditActivity : AppCompatActivity() {
	private lateinit var editTextTitle: AppCompatEditText
	private lateinit var editTextDescription: AppCompatEditText
	private lateinit var numPicker: NumberPicker

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_edit)

		findViews()
		title = "Add note"
	}

	fun findViews() {
		editTextTitle = findViewById(R.id.edit_text_title)
		editTextDescription = findViewById(R.id.edit_text_description)
		numPicker = findViewById(R.id.number_priority)
		numPicker.minValue = 0
		numPicker.maxValue = 10
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.save_menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId) {
			R.id.save_menu_item -> {
				saveNote()
			}
		}

		return super.onOptionsItemSelected(item)
	}

	fun saveNote() {
		val title = editTextTitle.text ?: return
		val description = editTextDescription.text ?: return
		val priority = numPicker.value

		if (title.trim().isEmpty() || description.trim().isEmpty()) {
			Toast.makeText(this@AddEditActivity, "Cannot leave empty title or description!", Toast.LENGTH_SHORT).show()
			return
		}

		val intent = Intent().apply {
			putExtra(KeyConstants.title.name, title)
			putExtra(KeyConstants.description.name, description)
			putExtra(KeyConstants.priority.name, priority)
		}

		setResult(CodeConstants.requestCode.code, intent)

		finish()
	}
}
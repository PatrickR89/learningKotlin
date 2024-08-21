package com.patrickr.firestoreproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
	private lateinit var editTextTitle: AppCompatEditText
	private lateinit var editTextDescription: AppCompatEditText
	private lateinit var saveButton: AppCompatButton
	private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		getViews()
	}

	private fun getViews() {
		editTextTitle = findViewById(R.id.edit_text_title)
		editTextDescription = findViewById(R.id.edit_text_description)
		saveButton = findViewById(R.id.button_save)
		saveButton.setOnClickListener {
			saveNote()
		}
	}

	private fun saveNote() {
		val title = editTextTitle.text.toString()
		val description = editTextDescription.text.toString()

		val note = mutableMapOf<String, Any>()
		note[DatabaseKeys.title.name] = title
		note[DatabaseKeys.description.name] = description

		db
			.collection("Collection")
			.document("My first note")
			.set(note)
			.addOnSuccessListener {
				Toast.makeText(this@MainActivity, "Note added!", Toast.LENGTH_LONG).show()
				editTextTitle.setText("")
				editTextDescription.setText("")
			}
			.addOnFailureListener {
				Toast.makeText(this@MainActivity, "Note not added!", Toast.LENGTH_LONG).show()
			}
	}
}

enum class DatabaseKeys {
	title, description;
}
package com.patrickr.firestoreproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
	private lateinit var editTextTitle: AppCompatEditText
	private lateinit var editTextDescription: AppCompatEditText
	private lateinit var saveButton: AppCompatButton
	private lateinit var loadButton: AppCompatButton
	private lateinit var textView: TextView
	private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
	private val docRef: DocumentReference = MainActivity.getDocumentRef(db)

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

		loadButton = findViewById(R.id.load_button)
		textView = findViewById(R.id.text_view_data)
		loadButton.setOnClickListener {
			loadNote()
		}
	}

	override fun onStart() {
		super.onStart()
		docRef.addSnapshotListener { value, error ->
			error?.let {
				return@addSnapshotListener
			}

			value?.let {
				require(it.exists())
				val title = it.getString(DatabaseKeys.title.name)
				val description = it.getString(DatabaseKeys.description.name)
				textView.text = "Title: $title. \nDescription: $description"
			}
		}
	}

	private fun loadNote() {
		docRef
			.get()
			.addOnSuccessListener { document ->
				require(document.exists())
				val title = document.getString(DatabaseKeys.title.name)
				val description = document.getString(DatabaseKeys.description.name)

				val note = mutableMapOf<String, Any>()

				title?.let {
					note[DatabaseKeys.title.name] = it
				}

				description?.let {
					note[DatabaseKeys.description.name] = it
				}

				textView.text = "Title: $title. \nDescription: $description"
			}
			.addOnFailureListener {
				Toast.makeText(this@MainActivity, "Failed to load.", Toast.LENGTH_LONG).show()
			}
	}

	private fun saveNote() {
		val title = editTextTitle.text.toString()
		val description = editTextDescription.text.toString()

		val note = mutableMapOf<String, Any>()
		note[DatabaseKeys.title.name] = title
		note[DatabaseKeys.description.name] = description

		docRef
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

	companion object {
		val collectionName: String = "Notebook"
		val documentName: String = "My first note"

		fun getDocumentRef(db: FirebaseFirestore): DocumentReference {
			return db.collection(collectionName).document(documentName)
		}
	}
}

enum class DatabaseKeys {
	title, description;
}
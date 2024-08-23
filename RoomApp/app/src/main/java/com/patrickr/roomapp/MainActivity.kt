package com.patrickr.roomapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.patrickr.roomapp.activities.AddEditActivity
import com.patrickr.roomapp.adaptors.NoteAdaptor

class MainActivity : AppCompatActivity() {

	private lateinit var noteViewModel: NoteViewModel
	private lateinit var recyclerView: RecyclerView
	private lateinit var noteAdaptor: NoteAdaptor
	private lateinit var addNoteButton: FloatingActionButton

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setupViewAndAdaptor()
		setupNoteViewModel()
		setupButton()
	}

	private fun setupNoteViewModel() {
		noteViewModel = ViewModelProvider(
			this,
			ViewModelProvider.AndroidViewModelFactory(application)
		)[NoteViewModel::class.java]

		noteViewModel.allNotes.observe(this) { list ->
			noteAdaptor.setNotes(list)
		}
	}

	private fun setupViewAndAdaptor() {
		addNoteButton = findViewById(R.id.add_note_btn)
		recyclerView = findViewById(R.id.recycler_view_notes)
		noteAdaptor = NoteAdaptor()
		recyclerView.adapter = noteAdaptor
		recyclerView.layoutManager = LinearLayoutManager(this)


	}

	private fun setupButton() {
		val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
			if (it.resultCode == CodeConstants.requestCode.code) {
				val title = it.data?.getStringExtra(KeyConstants.title.name) ?: return@registerForActivityResult
				val description = it.data?.getStringExtra(KeyConstants.description.name) ?: return@registerForActivityResult
				val priority = it.data?.getIntExtra(KeyConstants.priority.name, 10) ?: return@registerForActivityResult

				val note = Note(title, description, priority)
				noteViewModel.addNote(note)
			}
		}
		addNoteButton.setOnClickListener {
			val intent = Intent(this@MainActivity, AddEditActivity::class.java)
			getResult.launch(intent)
		}
	}
}
package com.patrickr.roomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.patrickr.roomapp.adaptors.NoteAdaptor

class MainActivity : AppCompatActivity() {
	private lateinit var noteViewModel: NoteViewModel
	private lateinit var recyclerView: RecyclerView
	private lateinit var noteAdaptor: NoteAdaptor

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setupViewAndAdaptor()
		setupNoteViewModel()
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
		recyclerView = findViewById(R.id.recycler_view_notes)
		noteAdaptor = NoteAdaptor()
		recyclerView.adapter = noteAdaptor
		recyclerView.layoutManager = LinearLayoutManager(this)

	}
}
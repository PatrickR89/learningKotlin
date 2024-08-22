package com.patrickr.roomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
	private lateinit var noteViewModel: NoteViewModel
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	fun setupNoteViewModel() {
		noteViewModel = ViewModelProvider(
			this,
			ViewModelProvider.AndroidViewModelFactory(application)
		)[NoteViewModel::class.java]

		noteViewModel.allNotes.observe(this) { list ->
			
		}
	}
}
package com.patrickr.roomapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
	val allNotes: LiveData<MutableList<Note>>
	val repository: NoteRepository

	init{
		println("Creating viewModel instance")
		val dao = NoteDatabase.getInstance(application).getNotesDao()
		repository = NoteRepository(dao)
		allNotes = repository.allNotes
	}

	fun deleteNote(note: Note) = viewModelScope.launch {
		repository.delete(note)
	}

	fun updateNote(note:Note) = viewModelScope.launch {
		repository.update(note)
	}

	fun addNote(note: Note) = viewModelScope.launch {
		repository.insert(note)
	}

	fun deletAll() {
		viewModelScope.launch {
			repository.deleteAllNotes()
		}
	}
}
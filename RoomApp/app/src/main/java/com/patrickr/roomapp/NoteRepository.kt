package com.patrickr.roomapp

import androidx.lifecycle.LiveData

class NoteRepository(private val notesDao: NoteDao) {
	val allNotes: LiveData<MutableList<Note>> = notesDao.getAllNotes()

	init {
		println("Creating repository instance")
	}

	suspend fun insert(note: Note) {
		notesDao.insert(note)
	}

	suspend fun delete(note: Note) {
		notesDao.delete(note)
	}

	suspend fun update(note: Note) {
		notesDao.update(note)
	}
}
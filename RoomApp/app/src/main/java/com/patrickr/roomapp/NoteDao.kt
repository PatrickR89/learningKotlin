package com.patrickr.roomapp

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface NoteDao {

	@Insert
	suspend fun insert(note: Note)

	@Delete
	suspend fun delete(note: Note)

	@Update
	suspend fun update(note: Note)

	@Query("DELETE FROM note_table")
	suspend fun deleteAll()

	@Query("SELECT * FROM note_table ORDER BY title_note ASC")
	fun getAllNotes(): LiveData<MutableList<Note>>
}
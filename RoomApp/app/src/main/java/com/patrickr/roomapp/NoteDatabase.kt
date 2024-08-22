package com.patrickr.roomapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
	abstract fun getNotesDao(): NoteDao

	companion object {
		private var instance: NoteDatabase? = null

		fun getInstance(context: Context): NoteDatabase {
			return instance ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					NoteDatabase::class.java,
					"note_database"
				).build()
				this.instance = instance
				return instance
			}
		}
	}
}
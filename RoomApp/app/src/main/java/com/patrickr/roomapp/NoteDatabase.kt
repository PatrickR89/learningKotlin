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
				println("Creating database instance")
				// REQUIRES KAPT!
				val instance = Room.databaseBuilder(
					context.applicationContext,
					NoteDatabase::class.java,
					"note_database"
				)
				println("Database instance created")
				val builtInstance = instance.build()
				println("Database instance built")

				this.instance = builtInstance
				println("Database instance created, storing and returning it")
				return builtInstance
			}
		}
	}
}
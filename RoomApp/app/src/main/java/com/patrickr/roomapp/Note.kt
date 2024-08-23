package com.patrickr.roomapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
data class Note(
	@ColumnInfo(name = "title_note")
	val title: String,
	val description: String,
	val priority: Int
	): Serializable {
	@PrimaryKey(autoGenerate = true)
	var id = 0
}

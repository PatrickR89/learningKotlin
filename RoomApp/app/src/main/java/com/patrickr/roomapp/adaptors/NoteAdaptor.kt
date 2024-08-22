package com.patrickr.roomapp.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.patrickr.roomapp.Note
import com.patrickr.roomapp.R

class NoteAdaptor(): RecyclerView.Adapter<NoteAdaptor.NoteViewHolder>() {
	private var notesList: MutableList<Note> = mutableListOf()
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
		return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
	}

	override fun getItemCount(): Int {
		return notesList.size
	}

	override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
		val note = notesList[position]
		holder.textTitle.text = note.title
		holder.textDescription.text = note.description
		holder.textPriority.text = note.priority.toString()
	}

	public fun setNotes(notes: MutableList<Note>) {
		notesList = notes
	}

	inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
		val textTitle = view.findViewById<TextView>(R.id.text_view_title)
		val textDescription = view.findViewById<TextView>(R.id.text_view_description)
		val textPriority = view.findViewById<TextView>(R.id.text_view_priority)
	}
}
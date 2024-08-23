package com.patrickr.roomapp.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patrickr.roomapp.Note
import com.patrickr.roomapp.R

class NoteAdaptor(val listener: NoteOnClickListener): ListAdapter<Note, NoteAdaptor.NoteViewHolder>(
	diffCallback) {
	private var notesList: MutableList<Note> = mutableListOf()
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
		return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
	}

	override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
		val note = getItem(position)
		holder.textTitle.text = note.title
		holder.textDescription.text = note.description
		holder.textPriority.text = note.priority.toString()
	}

	fun getNoteAt(position: Int): Note {
		return getItem(position)
	}

	inner class NoteViewHolder(val view: View): RecyclerView.ViewHolder(view) {
		val textTitle = view.findViewById<TextView>(R.id.text_view_title)
		val textDescription = view.findViewById<TextView>(R.id.text_view_description)
		val textPriority = view.findViewById<TextView>(R.id.text_view_priority)

		init {
			view.setOnClickListener {
				if(adapterPosition != RecyclerView.NO_POSITION) {
					listener.onItemClick(getItem(adapterPosition))
				}
			}
		}
	}

	companion object {
		private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
			override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
				return oldItem == newItem
			}

		}
	}
}

interface NoteOnClickListener {
	fun onItemClick(note: Note)
}
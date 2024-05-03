package com.patrickr.top10apps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FeedAdapter(
	context: Context,
	private val resource: Int,
	private val applications: List<FeedEntry>
	): ArrayAdapter<FeedEntry>(context, resource) {
	private val TAG = "Feed Adapter"
	private val inflater = LayoutInflater.from(context)

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		Log.d(TAG, "getView(position: Int, convertView: View?, parent: ViewGroup)")
		val view = inflater.inflate(resource, parent, false)
		val nameTextView: TextView = view.findViewById(R.id.titleTextView)
		val artistTextView: TextView = view.findViewById(R.id.artistTextView)
		val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
		val currentApp = applications[position]
		nameTextView.text = currentApp.name
		artistTextView.text = currentApp.artist
		descriptionTextView.text = currentApp.summary

		return view
	}

	override fun getCount(): Int {
		Log.d(TAG, "getCount()")
		return applications.size
	}
}
package com.patrickr.top10apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class FeedAdapter(
	context: Context,
	private val resource: Int,
	private val applications: List<FeedEntry>
	): ArrayAdapter<FeedEntry>(context, resource) {
	private val TAG = "Feed Adapter"
	private val inflater = LayoutInflater.from(context)

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val view: View
		val viewHolder: ViewHolder
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false)
			viewHolder = ViewHolder(view)
			view.tag = viewHolder
		} else {
			view = convertView
			viewHolder = view.tag as ViewHolder
		}

		val currentApp = applications[position]
		viewHolder.textViewName.text = currentApp.name
		viewHolder.textViewArtist.text = currentApp.artist
		viewHolder.textViewDescription.text = currentApp.summary

		return view
	}

	override fun getCount(): Int {
		return applications.size
	}
}
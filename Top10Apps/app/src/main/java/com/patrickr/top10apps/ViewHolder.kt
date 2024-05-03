package com.patrickr.top10apps

import android.view.View
import android.widget.TextView

class ViewHolder(view: View) {
	var textViewName: TextView = view.findViewById(R.id.titleTextView)
	var textViewArtist: TextView = view.findViewById(R.id.artistTextView)
	var textViewDescription: TextView = view.findViewById(R.id.descriptionTextView)
}
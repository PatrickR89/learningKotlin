package com.patrickr.fragments.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.patrickr.fragments.R

class FragmentB: Fragment() {
	private lateinit var editText: EditText
	private lateinit var button: Button
	private lateinit var listener: FragmentBListener

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_b, container,false)
		editText = view.findViewById(R.id.edit_text_b)
		button = view.findViewById(R.id.sendButton_b)

		button.setOnClickListener {
			listener.sendFromB(editText.text.toString())
		}

		return view
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
//		if (context is FragmentBListener) {
//			listener = context
//		}
		require(context is FragmentBListener)
		listener = context
	}

	public fun updateText(text: String) {
		editText.setText(text)
	}
}

interface FragmentBListener {
	fun sendFromB(input: String)
}
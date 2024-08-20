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

class FragmentA: Fragment() {
	private lateinit var editText: EditText
	private lateinit var button: Button
	private lateinit var listener:FragmentAListener

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_a, container,false)
		editText = view.findViewById(R.id.edit_text_a)
		button = view.findViewById(R.id.sendButton_a)

		button.setOnClickListener {
			listener.sendFromA(editText.text.toString())
		}

		return view
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		require(context is FragmentAListener)
		listener = context
	}

	public fun updateText(text: String) {
		editText.setText(text)
	}
}

interface FragmentAListener {
	fun sendFromA(input: String)
}
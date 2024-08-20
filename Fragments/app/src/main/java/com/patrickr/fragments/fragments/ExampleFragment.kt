package com.patrickr.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.patrickr.fragments.R

class ExampleFragment: Fragment() {
	private var name: String = ""
	private var age: Int = 0
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.example_fragment, container, false)
		val textView: TextView = view.findViewById(R.id.text_view)
		arguments?.let {
			name = it.getString(ExampleFragmentArguments.userName.name, "No name")
			age = it.getInt(ExampleFragmentArguments.userAge.name, 0)
		}
		textView.text = "$name \n" + age.toString()
		return view
	}

	companion object {

		fun newInstance(text: String, number: Int): ExampleFragment {
			val fragment = ExampleFragment()
			val bundle = Bundle()
			bundle.putString(ExampleFragmentArguments.userName.name, text)
			bundle.putInt(ExampleFragmentArguments.userAge.name,number)
			fragment.arguments = bundle
			return fragment
		}

		fun newInstanceKotlin(text: String, number: Int): ExampleFragment {
			return ExampleFragment().apply {
				arguments = Bundle().apply {
					putString(ExampleFragmentArguments.userName.name, text)
					putInt(ExampleFragmentArguments.userAge.name,number)
				}
			}
		}
	}
}

enum class ExampleFragmentArguments {
	userName, userAge;
}
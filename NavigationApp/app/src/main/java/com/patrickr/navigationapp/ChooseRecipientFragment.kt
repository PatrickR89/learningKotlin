package com.patrickr.navigationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation

class ChooseRecipientFragment : Fragment() {

	private lateinit var navController: NavController

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_choose_recipient, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		navController = Navigation.findNavController(view)
		findButton()
	}

	private fun findButton() {
		val nextButton = view?.findViewById<Button>(R.id.next_btn)
		val cancelButton = view?.findViewById<Button>(R.id.cancel_btn)

		nextButton?.setOnClickListener {
			navController.navigate(R.id.action_chooseRecipientFragment_to_specifyAmountFragment)
		}

		cancelButton?.setOnClickListener {
			activity?.onBackPressedDispatcher?.onBackPressed()
		}
	}
}
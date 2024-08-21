package com.patrickr.navigationapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText

class ChooseRecipientFragment : Fragment() {

	private lateinit var navController: NavController
	private lateinit var recipient: TextInputEditText

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
		getRecipientInput()
	}

	private fun findButton() {
		val nextButton = view?.findViewById<Button>(R.id.next_btn)
		val cancelButton = view?.findViewById<Button>(R.id.cancel_btn)

		nextButton?.setOnClickListener {
			if(!TextUtils.isEmpty(recipient.text.toString())) {
				val bundle = bundleOf("recipient" to recipient.text.toString())
				navController.navigate(R.id.action_chooseRecipientFragment_to_specifyAmountFragment, bundle)
			}
		}

		cancelButton?.setOnClickListener {
			activity?.onBackPressedDispatcher?.onBackPressed()
		}
	}

	private fun getRecipientInput() {
		val recipient: TextInputEditText? = view?.findViewById(R.id.input_recipient)
		recipient?.let {
			this.recipient = recipient
		}
	}
}
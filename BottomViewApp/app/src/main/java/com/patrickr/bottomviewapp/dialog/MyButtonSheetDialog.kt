package com.patrickr.bottomviewapp.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrickr.bottomviewapp.R

class MyButtonSheetDialog: BottomSheetDialogFragment() {
	var listener: BottomSheetListener? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
		val button1: AppCompatButton = view.findViewById(R.id.button_one)
		val button2: AppCompatButton = view.findViewById(R.id.button_two)

		button1.setOnClickListener {
			listener?.onButtonClicked("Button one")
			dismiss()
		}
		button2.setOnClickListener {
			listener?.onButtonClicked("Button two")
			dismiss()
		}
		return view
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)

		if (context is BottomSheetListener) {
			listener = context
		}
	}

}

interface BottomSheetListener {
	fun onButtonClicked(input: String)
}
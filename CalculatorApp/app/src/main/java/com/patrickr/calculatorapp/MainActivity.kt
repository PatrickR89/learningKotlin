package com.patrickr.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.patrickr.calculatorapp.databinding.ActivityMainBinding
import java.lang.NumberFormatException

private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND_ONE = "OperandOne"
private const val STATE_OPERAND_ONE_STORED = "OperandOne_Stored"

class MainActivity : AppCompatActivity() {
	private lateinit var activityMain: ActivityMainBinding

	private var operandOne: Double? = null
	private var pendingOperation = "="

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityMain = ActivityMainBinding.inflate(layoutInflater)
		setContentView(activityMain.root)
		setupInputs(activityMain)
	}

	private fun setupInputs(binding: ActivityMainBinding) {
		val listener = View.OnClickListener { view ->
			val button = view as Button
			binding.newNumber.append(button.text)
		}

		binding.button0.setOnClickListener(listener)
		binding.button1.setOnClickListener(listener)
		binding.button2.setOnClickListener(listener)
		binding.button3.setOnClickListener(listener)
		binding.button4.setOnClickListener(listener)
		binding.button5.setOnClickListener(listener)
		binding.button6.setOnClickListener(listener)
		binding.button7.setOnClickListener(listener)
		binding.button8.setOnClickListener(listener)
		binding.button9.setOnClickListener(listener)
		binding.buttonDot.setOnClickListener(listener)

		val opListener = View.OnClickListener { view ->
			val operation = (view as Button).text.toString()
			try {
				val value = binding.newNumber.text.toString().toDouble()
				performOperation(value, operation)
			} catch (e: NumberFormatException) {
				binding.newNumber.setText("")
			}


			pendingOperation = operation
			binding.operation.text = pendingOperation
		}

		binding.buttonEqual.setOnClickListener(opListener)
		binding.buttonPlus.setOnClickListener(opListener)
		binding.buttonMinus.setOnClickListener(opListener)
		binding.buttonDivide.setOnClickListener(opListener)
		binding.buttonMultiply.setOnClickListener(opListener)
	}

	private fun performOperation(value: Double, operation: String) {
		var operand = operandOne ?: run {
			operandOne = value
			displayOperand(value)
			return
		}

		if (pendingOperation == "=") {
			pendingOperation = operation
		}

		when (pendingOperation) {
			"=" -> operand = value
			"/" -> operand = if (value == 0.0) {
				Double.NaN
			} else {
				operand / value
			}

			"+" -> operand += value
			"-" -> operand -= value
			"*" -> operand *= value
		}
		displayOperand(operand)
	}

	private fun displayOperand(operand: Double) {
		activityMain.result.setText(operand.toString())
		activityMain.newNumber.setText("")
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)

		outState.putString(STATE_PENDING_OPERATION, pendingOperation)
		val operandValue = operandOne ?: return
		outState.putDouble(STATE_OPERAND_ONE, operandValue)
		outState.putBoolean(STATE_OPERAND_ONE_STORED, true)
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)
		operandOne = if (
			savedInstanceState.getBoolean(STATE_OPERAND_ONE_STORED, false)
		) {
			savedInstanceState.getDouble(STATE_OPERAND_ONE)
		} else {
			null
		}

		pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION, "=")
		activityMain.operation.text = pendingOperation
	}
}
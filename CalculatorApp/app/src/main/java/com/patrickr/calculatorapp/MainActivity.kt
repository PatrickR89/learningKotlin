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
			activityMain.newNumber.append(button.text)
		}

		activityMain.button0.setOnClickListener(listener)
		activityMain.button1.setOnClickListener(listener)
		activityMain.button2.setOnClickListener(listener)
		activityMain.button3.setOnClickListener(listener)
		activityMain.button4.setOnClickListener(listener)
		activityMain.button5.setOnClickListener(listener)
		activityMain.button6.setOnClickListener(listener)
		activityMain.button7.setOnClickListener(listener)
		activityMain.button8.setOnClickListener(listener)
		activityMain.button9.setOnClickListener(listener)
		activityMain.buttonDot.setOnClickListener(listener)
		activityMain.buttonNegative.setOnClickListener { _ ->
			val value = activityMain.newNumber.text.toString()
			if (value.isEmpty()) {
				activityMain.newNumber.setText("-")
			} else {
				try {
					var doubleValue = value.toDouble()
					doubleValue *= -1
					activityMain.newNumber.setText(doubleValue.toString())
				} catch (error: NumberFormatException) {
					activityMain.newNumber.setText("")
				}
			}
		}

		val opListener = View.OnClickListener { view ->
			val operation = (view as Button).text.toString()
			try {
				val value = activityMain.newNumber.text.toString().toDouble()
				performOperation(value, operation)
			} catch (e: NumberFormatException) {
				activityMain.newNumber.setText("")
			}


			pendingOperation = operation
			activityMain.operation.text = pendingOperation
		}

		activityMain.buttonEqual.setOnClickListener(opListener)
		activityMain.buttonPlus.setOnClickListener(opListener)
		activityMain.buttonMinus.setOnClickListener(opListener)
		activityMain.buttonDivide.setOnClickListener(opListener)
		activityMain.buttonMultiply.setOnClickListener(opListener)
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
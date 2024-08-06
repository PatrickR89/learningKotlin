package com.patrickr.radiobuttons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener

class MainActivity : AppCompatActivity() {
	private lateinit var radioGroup: RadioGroup
	private lateinit var radioButton: RadioButton
	private lateinit var seekBar: SeekBar
	private lateinit var seekTextView: TextView
	private lateinit var toggleButton: ToggleButton
	private lateinit var toggleText: TextView
	private lateinit var checkBoxOne: CheckBox
	private lateinit var checkBoxTwo: CheckBox
	private lateinit var checkBoxThree: CheckBox
	private lateinit var checkBoxButton: Button
	private lateinit var checkBoxChoice: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		createRadioButtons()
		createSeekBar()
		createToggle()
		createCheckBox()
	}

	fun createRadioButtons() {
		radioGroup = findViewById(R.id.radioGroup)

		radioGroup.setOnCheckedChangeListener { radioGroup, id ->
			radioButton = findViewById(id)

			when(radioButton.id) {
				R.id.radioButton1 -> {
					Toast.makeText(this@MainActivity, "Glad you like it.", Toast.LENGTH_SHORT).show()
				}
				R.id.radioButton2 -> {
					Toast.makeText(this@MainActivity, "Sad you don't..", Toast.LENGTH_SHORT).show()
				}
				R.id.radioButton3 -> {
					Toast.makeText(this@MainActivity, "It's not that bad..", Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	fun createSeekBar() {
		seekBar = findViewById(R.id.seekBar)
		seekTextView = findViewById(R.id.seekText)

		seekBar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
			override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
				seekTextView.text = "Rate " + seekBar.progress.toString()
			}

			override fun onStartTrackingTouch(p0: SeekBar?) {
				Toast.makeText(this@MainActivity, "onStartTrackingTouch", Toast.LENGTH_SHORT).show()
			}

			override fun onStopTrackingTouch(p0: SeekBar?) {
				Toast.makeText(this@MainActivity, "onStopTrackingTouch", Toast.LENGTH_SHORT).show()
			}
		})
	}

	fun createToggle() {
		toggleButton = findViewById(R.id.toggleButton)
		toggleText = findViewById(R.id.toggleTextView)

		toggleButton.setOnCheckedChangeListener { compoundButton, isChecked ->
			if (isChecked) {
				toggleText.visibility = View.VISIBLE
			} else {
				toggleText.visibility = View.INVISIBLE
			}
		}
	}

	fun createCheckBox() {
		checkBoxOne = findViewById(R.id.checkBox)
		checkBoxTwo = findViewById(R.id.checkBox2)
		checkBoxThree = findViewById(R.id.checkBox3)
		checkBoxChoice = findViewById(R.id.checkBoxText2)
		checkBoxButton = findViewById(R.id.checkBoxButton)

		checkBoxButton.setOnClickListener {
			val stringBuilder = StringBuilder()
			stringBuilder.append(checkBoxOne.text.toString() + " status is: " + checkBoxOne.isChecked + ".\n")
			stringBuilder.append(checkBoxTwo.text.toString() + " status is: " + checkBoxTwo.isChecked + ".\n")
			stringBuilder.append(checkBoxThree.text.toString() + " status is: " + checkBoxThree.isChecked + ".\n")

			checkBoxChoice.text = stringBuilder.toString()
		}
	}
}
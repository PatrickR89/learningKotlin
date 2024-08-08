package com.patrickr.quizapp.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.patrickr.quizapp.R
import com.patrickr.quizapp.model.Question
import com.patrickr.quizapp.utils.Constants
import com.patrickr.quizapp.utils.KeyConstants

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {
	private lateinit var progressBar: ProgressBar
	private lateinit var textViewProgress: TextView
	private lateinit var textViewQuestion: TextView
	private lateinit var flagImage: ImageView

	private lateinit var textViewOptionOne: TextView
	private lateinit var textViewOptionTwo: TextView
	private lateinit var textViewOptionThree: TextView
	private lateinit var textViewOptionFour: TextView
	private lateinit var checkButton: Button

	private var current: Int = 1
	private var selectedOption = 0
	private lateinit var currentQuestion: Question
	private var answered = false
	private lateinit var questionsList: MutableList<Question>
	private var score = 0
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_questions)
		findAllViews()
		questionsList = Constants.getQuestions()
		setQuestion()
	}

	private fun findAllViews() {
		progressBar = findViewById(R.id.progress_bar)
		textViewProgress = findViewById(R.id.text_view_progress)
		textViewQuestion = findViewById(R.id.questions_text_view)
		flagImage = findViewById(R.id.image_flag)

		textViewOptionOne = findViewById(R.id.text_view_optionOne)
		textViewOptionTwo = findViewById(R.id.text_view_optionTwo)
		textViewOptionThree = findViewById(R.id.text_view_optionThree)
		textViewOptionFour = findViewById(R.id.text_view_optionFour)
		checkButton = findViewById(R.id.button_check)

		textViewOptionOne.setOnClickListener(this)
		textViewOptionTwo.setOnClickListener(this)
		textViewOptionThree.setOnClickListener(this)
		textViewOptionFour.setOnClickListener(this)
		checkButton.setOnClickListener(this)
	}

	private fun setQuestion() {
		if (current == questionsList.size) {
			checkButton.text = getString(R.string.finish)

			Intent(this, ResultActivity::class.java).also {
				val name = this.intent.getStringExtra(KeyConstants.userName.key)
				name.let { name ->
					it.putExtra(KeyConstants.userName.key, name)
				}
				it.putExtra(KeyConstants.score.key, score)
				it.putExtra(KeyConstants.totalQuestions.key, questionsList.size)
				startActivity(it)
			}
		} else {
			checkButton.text = getString(R.string.check)
			currentQuestion = questionsList[current - 1]

			val question = questionsList[current - 1]
			flagImage.setImageResource(question.image)
			progressBar.setProgress(current)
			textViewProgress.text = "$current/${progressBar.max}"
			textViewQuestion.text = question.question
			textViewOptionOne.text = question.optionOne
			textViewOptionTwo.text = question.optionTwo
			textViewOptionThree.text = question.optionThree
			textViewOptionFour.text = question.optionFour
		}

		current++
		answered = false
		resetOptions()
	}

	private fun resetOptions() {
		val options = mutableListOf<TextView>()
		options.add(textViewOptionOne)
		options.add(textViewOptionTwo)
		options.add(textViewOptionThree)
		options.add(textViewOptionFour)
		selectedOption = 0
		for (option in options) {
			option.setTextColor(Color.parseColor("#7a8089"))
			option.typeface = Typeface.DEFAULT
			option.background = ContextCompat.getDrawable(
				this,
				R.drawable.default_option_border_bg
			)
		}
	}

	private fun selectedOption(textView: TextView, selectedOptionNumber: Int) {
		resetOptions()

		selectedOption = selectedOptionNumber
		textView.setTextColor(Color.parseColor("#363a43"))
		textView.setTypeface(textView.typeface, Typeface.BOLD)
		textView.background = ContextCompat.getDrawable(
			this,
			R.drawable.selected_option_border_bg
		)
	}

	override fun onClick(p0: View?) {
		when(p0?.id) {
			R.id.text_view_optionOne -> {
				selectedOption(textViewOptionOne, 1)
			}
			R.id.text_view_optionTwo -> {
				selectedOption(textViewOptionTwo, 2)
			}
			R.id.text_view_optionThree -> {
				selectedOption(textViewOptionThree, 3)
			}
			R.id.text_view_optionFour -> {
				selectedOption(textViewOptionFour, 4)
			}
			R.id.button_check -> {
				if (answered) {
					setQuestion()
				} else {
					checkAnswer()
				}
			}
		}
	}

	private fun checkAnswer() {
		answered = true

		if (selectedOption == currentQuestion.correctAnswer) {
			highlightAnswer(selectedOption)
			score ++
		} else {
			highlightAnswer(selectedOption, false)
			highlightAnswer(currentQuestion.correctAnswer)
		}

		checkButton.text = "Next"
	}

	private fun highlightAnswer(answer: Int, correct: Boolean = true) {
		var background = R.drawable.correct_option_border_bg

		if (!correct) {
			background = R.drawable.incorrect_option_border_bg
		}

		when(answer) {
			1 -> textViewOptionOne.background = ContextCompat.getDrawable(this@QuestionsActivity, background)
			2 -> textViewOptionTwo.background = ContextCompat.getDrawable(this@QuestionsActivity, background)
			3 -> textViewOptionThree.background = ContextCompat.getDrawable(this@QuestionsActivity, background)
			4 -> textViewOptionFour.background = ContextCompat.getDrawable(this@QuestionsActivity, background)
		}
	}
}
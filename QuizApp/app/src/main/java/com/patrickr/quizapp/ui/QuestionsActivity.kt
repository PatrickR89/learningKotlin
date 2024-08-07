package com.patrickr.quizapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.patrickr.quizapp.R
import com.patrickr.quizapp.model.Question
import com.patrickr.quizapp.utils.Constants

class QuestionsActivity : AppCompatActivity() {
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
	private lateinit var questionsList: MutableList<Question>
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
	}

	private fun setQuestion() {
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
}
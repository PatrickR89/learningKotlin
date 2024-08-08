package com.patrickr.quizapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.patrickr.quizapp.MainActivity
import com.patrickr.quizapp.R
import com.patrickr.quizapp.utils.KeyConstants

class ResultActivity : AppCompatActivity() {

	private lateinit var textViewScore: TextView
	private lateinit var textViewName: TextView
	private lateinit var button: AppCompatButton

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_result)

		textViewName = findViewById(R.id.tv_congratulations)
		textViewScore = findViewById(R.id.tv_score)
		button = findViewById(R.id.btn_finish)

		val totalQuestions = intent.getStringExtra(KeyConstants.userName.key)
		totalQuestions?.let {
			textViewName.text = String.format(getString(R.string.congrats), it)
		}

		val totalScore = intent.getIntExtra(KeyConstants.totalQuestions.key, 10)
		val score = intent.getIntExtra(KeyConstants.score.key, 0)

		textViewScore.text = String.format(getString(R.string.final_score), score, totalScore)

		button.setOnClickListener {
			Intent(this, MainActivity::class.java).also {
				startActivity(it)
				finish()
			}
		}
	}
}
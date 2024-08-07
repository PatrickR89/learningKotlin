package com.patrickr.quizapp.model

data class Question(
	val id: Int,
	val image: Int,
	val optionOne: String,
	val optionTwo: String,
	val optionThree: String,
	val optionFour: String,
	val correctAnswer: Int,
	val question: String = "What country does this flag belong to?"
)

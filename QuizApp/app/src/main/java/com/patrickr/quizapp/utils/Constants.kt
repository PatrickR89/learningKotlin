package com.patrickr.quizapp.utils

import com.patrickr.quizapp.R
import com.patrickr.quizapp.model.Question

enum class KeyConstants() {
	userName,
	totalQuestions,
	score;

	val key: String
		get() {
			return when(this) {
				userName -> "user_name"
				totalQuestions -> "total_questions"
				score -> "correct_answers"
			}
		}
}

class Constants {
	companion object {

		fun getQuestions(): MutableList<Question> {
			val questions = mutableListOf<Question>()
			val quest1 = Question(
				1,
				R.drawable.italy_flag, "Italy",
				"India", "Iran",
				"Ireland",
				1
			)
			questions.add(quest1)

			val quest2 = Question(
				2,
				R.drawable.argentina_flag,
				"Armenia", "Argentina",
				"Austria", "Australia",
				2
			)
			questions.add(quest2)

			val quest3 = Question(
				3,
				R.drawable.brazil_flag,
				"Belarus", "Belgium",
				"Bangladesh", "Brazil",
				4
			)
			questions.add(quest3)

			val quest4 = Question(
				4,
				R.drawable.france_flag,
				"Finland", "Fiji",
				"France", "None of the options",
				3
			)
			questions.add(quest4)

			val quest5 = Question(
				5,
				R.drawable.finland_flag,
				"Finland", "Fiji",
				"France", "None of the above to",
				1
			)
			questions.add(quest5)

			val quest6 = Question(
				6,
				R.drawable.germany_flag,
				"Gambia", "Germany",
				"Georgia", "Greece",
				2
			)
			questions.add(quest6)

			val quest7 = Question(
				7,
				R.drawable.nigeria_flag,
				"Netherlands", "Nicaragua",
				"Nigeria", "Nepal.",
				3
			)
			questions.add(quest7)

			val quest8 = Question(
				8,
				R.drawable.romania_flag,
				"Russia", "Rwanda",
				"None of the options", "Romania",
				4
			)
			questions.add(quest8)

			val quest9 = Question(
				9,
				R.drawable.spain_flag,
				"Serbia", "Spain",
				"Saudi Arabia", "Slovenia",
				2
			)
			questions.add(quest9)

			val quest10 = Question(
				10,
				R.drawable.haiti_flag,
				"Honduras", "Hungary",
				"Haiti", "None of the options",
				3
			)
			questions.add(quest10)
			return questions
		}
	}
}
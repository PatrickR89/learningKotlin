package com.patrickr.roomapp

enum class CodeConstants {
	addRequestCode,
	editRequestCode;

	val code: Int
		get() = when(this) {
			addRequestCode -> 1
			editRequestCode -> 2
		}
}

enum class KeyConstants {
	title,
	description,
	priority,
	note;
}
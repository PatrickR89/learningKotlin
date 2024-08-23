package com.patrickr.roomapp

enum class CodeConstants {
	requestCode;

	val code: Int
		get() = when(this) {
			CodeConstants.requestCode -> {
				 111
			}
		}
}

enum class KeyConstants {
	title,
	description,
	priority;
}
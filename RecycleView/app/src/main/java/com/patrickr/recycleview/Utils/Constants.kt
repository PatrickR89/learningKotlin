package com.patrickr.recycleview.Utils

enum class Constants {
	title, description;

	public val key: String
		get() {
			return when(this) {
				title -> "title";
				description -> "description"
			}
		}
}
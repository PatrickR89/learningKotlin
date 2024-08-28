package com.patrickr.retrofit.model

import com.google.gson.annotations.SerializedName

data class Post(
	val userId: Int,
	val title: String?,
	@SerializedName("body") val text: String
) {
	var id: Int = 0
}

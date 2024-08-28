package com.patrickr.retrofit.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface JsonPlaceHolderApi {

	@GET("posts")
	fun getPost(
		@Query("_sort") sort: String,
		@Query("_order") order: String,
		@Query("userId") vararg userId: Int,
	): Call<MutableList<Post>>

	@GET("posts")
	fun getPost(
		@QueryMap parameters: Map<String, String>
	): Call<MutableList<Post>>

	@GET("posts/{id}/comments")
	fun getPostComments(@Path("id") postId: Int): Call<MutableList<Comment>>

	@GET
	fun getPostComments(@Url url: String): Call<MutableList<Comment>>

	@POST("posts")
	fun createPost(@Body post: Post): Call<Post>

	@FormUrlEncoded
	@POST("posts")
	fun createPost(
		@Field("userId") userId: Int,
		@Field("title") title: String,
		@Field("body") text: String
	): Call<Post>

	// also available @FieldMap : MutableMap

	@PUT("posts/{id}")
	fun putPost(@Path("id") id: Int, @Body post: Post): Call<Post>

	@PATCH("posts/{id}")
	fun patchPost(@Path("id") id: Int, @Body post: Post): Call<Post>

	@DELETE("posts/{id}")
	fun deletePost(@Path("id") id: Int): Call<Unit>
}
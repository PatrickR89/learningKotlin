package com.patrickr.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.patrickr.retrofit.model.Comment
import com.patrickr.retrofit.model.JsonPlaceHolderApi
import com.patrickr.retrofit.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
	private lateinit var textView: TextView
	val retrofit: Retrofit by lazy<Retrofit> {
		Retrofit.Builder()
			.baseUrl("https://jsonplaceholder.typicode.com/")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	private val jsonPlaceHolderApi: JsonPlaceHolderApi by lazy<JsonPlaceHolderApi> {
		retrofit.create(JsonPlaceHolderApi::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		textView = findViewById(R.id.text_view_result)

//		getPosts()
//		getComments()
//		createPost()
//		updatePost()
	deletePost()
	}

	fun getPosts() {
		val params = mutableMapOf<String, String>()
		params["userId"] = "1"
		params["_sort"] = "id"
		params["_order"] = "desc"
		val call = jsonPlaceHolderApi.getPost(params)
		call.enqueue(object : Callback<MutableList<Post>> {
			override fun onResponse(p0: Call<MutableList<Post>>, p1: Response<MutableList<Post>>) {
				if (p1.isSuccessful) {
					val posts = p1.body()
					if (posts == null) {
						Toast.makeText(this@MainActivity, "Error: Response body empty", Toast.LENGTH_SHORT)
							.show()
						return
					}
					for (post in posts) {
						var content = ""
						content += "\n\n $post"
						textView.append(content)
					}
				} else {
					textView.text = p1.code().toString()
				}
			}

			override fun onFailure(p0: Call<MutableList<Post>>, p1: Throwable) {
				Toast.makeText(this@MainActivity, "Error: ${p1.toString()}", Toast.LENGTH_SHORT).show()
			}
		})
	}

	private fun getComments() {
//		val call = jsonPlaceHolderApi.getPostComments(4)
		val call = jsonPlaceHolderApi.getPostComments("posts/2/comments")
		call.enqueue(object : Callback<MutableList<Comment>> {
			override fun onResponse(p0: Call<MutableList<Comment>>, p1: Response<MutableList<Comment>>) {
				if (p1.isSuccessful) {
					val comments = p1.body()
					if (comments == null) {
						Toast.makeText(this@MainActivity, "Error: Response body empty", Toast.LENGTH_SHORT)
							.show()
						return
					}
					for (comment in comments) {
						var content = ""
						content += "\n\n $comment"
						textView.append(content)
					}
				} else {
					textView.text = p1.code().toString()
				}
			}

			override fun onFailure(p0: Call<MutableList<Comment>>, p1: Throwable) {
				Toast.makeText(this@MainActivity, "Error: ${p1.toString()}", Toast.LENGTH_SHORT).show()
			}

		})
	}

	fun createPost() {
		val post = Post(20, "New Title", "Some text")
//		val call = jsonPlaceHolderApi.createPost(post)
		val call = jsonPlaceHolderApi.createPost(1, "Hahahah", "Texty")
		call.enqueue(object : Callback<Post> {
			override fun onResponse(p0: Call<Post>, p1: Response<Post>) {
				if (p1.isSuccessful) {
					val postedPost = p1.body()
					if (postedPost == null) {
						Toast.makeText(this@MainActivity, "Error: Response body empty", Toast.LENGTH_SHORT)
							.show()
						return
					}
					textView.text = postedPost.toString()
				} else {
					textView.text = p1.code().toString()
				}
			}

			override fun onFailure(p0: Call<Post>, p1: Throwable) {
				Toast.makeText(this@MainActivity, "Error: ${p1.toString()}", Toast.LENGTH_SHORT).show()
			}
		})
	}

	fun updatePost() {
		val post = Post(20, null, "Some text")
		val call = jsonPlaceHolderApi.patchPost(2, post)
		call.enqueue(object : Callback<Post> {
			override fun onResponse(p0: Call<Post>, p1: Response<Post>) {
				if (p1.isSuccessful) {
					val postedPost = p1.body()
					if (postedPost == null) {
						Toast.makeText(this@MainActivity, "Error: Response body empty", Toast.LENGTH_SHORT)
							.show()
						return
					}
					textView.text = postedPost.toString()
				} else {
					textView.text = p1.code().toString()
				}
			}

			override fun onFailure(p0: Call<Post>, p1: Throwable) {
				Toast.makeText(this@MainActivity, "Error: ${p1.toString()}", Toast.LENGTH_SHORT).show()
			}
		})
	}

	fun deletePost() {
		val call = jsonPlaceHolderApi.deletePost(3)
		call.enqueue(object: Callback<Unit> {
			override fun onResponse(p0: Call<Unit>, p1: Response<Unit>) {
				Toast.makeText(this@MainActivity, "Delete post status: ${p1.code().toString()}", Toast.LENGTH_SHORT).show()
			}

			override fun onFailure(p0: Call<Unit>, p1: Throwable) {
				Toast.makeText(this@MainActivity, "Error: ${p1.toString()}", Toast.LENGTH_SHORT).show()
			}

		})
	}
}
package com.patrickr.authapp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
	private val view: MainView by lazy<MainView> {
		val mainView = MainView(this)
		mainView
	}

	private val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
		if(result.resultCode == Activity.RESULT_OK) {
			val data = result.data ?: return@registerForActivityResult
			this.viewModel.appAuth.endLogin(data)
		}
	}

	lateinit var viewModel: MainActivityViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		Log.d("MainActivity", "onCreate() called.")
		super.onCreate(savedInstanceState)
		Log.d("MainActivity", "super.onCreate() called")
		viewModel = MainActivityViewModel(this)
		setContentView(view)
		view.addButtonAction {
			startAuthentication()
		}

		observerViewModel()
		handleBrowserOpen()
	}

	fun handleBrowserOpen() {
		Log.d("MainActivity", "handleBrowserOpen() called")
		val action = intent.action
		if (action == Intent.ACTION_VIEW) {
			viewModel.appAuth.endLogin(intent)
		}
	}

	fun observerViewModel() {
		viewModel.loginStarted.observe( this) {event ->
			event?.getData()?.let {
				this.loginLauncher.launch(it)
			}
		}

		viewModel.loginCompleted.observe(this) { event ->
			event?.getData()?.let {
				Log.d(ContentValues.TAG, "Logged in!")
			}
		}
	}

	fun startAuthentication() {
			viewModel.startLogin()
	}
}
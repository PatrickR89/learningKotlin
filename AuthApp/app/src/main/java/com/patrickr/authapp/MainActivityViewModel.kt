package com.patrickr.authapp

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.patrickr.authapp.authentication.ApplicationException
import com.patrickr.authapp.authentication.AuthenticationConfig
import com.patrickr.authapp.authentication.AuthenticationHandler
import com.patrickr.authapp.authentication.AuthenticationHandlerListener
import com.patrickr.authapp.authentication.AuthenticationStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse

class MainActivityViewModel(activity: MainActivity): AndroidViewModel(activity.application),
	AuthenticationHandlerListener {

	val appAuth: AuthenticationHandler = AuthenticationHandler(activity)
	var loginStarted = MutableLiveData<Event<Intent>>()
	var loginCompleted = MutableLiveData<Event<Boolean>>()

	init {
		appAuth.listener = this
	}

	fun startLogin() {
		appAuth.startLogin()
	}

	override fun loginStarted(intent: Intent) {
		this@MainActivityViewModel.loginStarted.postValue(Event(intent))
	}

	override fun loginFinished(success: Boolean) {
		this@MainActivityViewModel.loginCompleted.postValue(Event(success))
	}
//	fun startLogin() {
//		var metadata = this.state.metadata
//
//		CoroutineScope(Dispatchers.IO).launch {
//			try {
//				if (metadata == null) {
//					metadata = appAuth.fetchMetadata()
//				}
//
//				withContext(Dispatchers.Main) {
//					this@MainActivityViewModel.state.metadata = metadata
//					metadata?.let {
//						val intent = appAuth.getAuthorizationRedirectIntent(it)
//						this@MainActivityViewModel.loginStarted.postValue(Event(intent))
//					}
//				}
//			} catch (exception: ApplicationException) {
//				withContext(Dispatchers.Main) {
//					Toast.makeText(this@MainActivityViewModel.app.applicationContext, "Login Error!", Toast.LENGTH_SHORT).show()
//				}
//			}
//		}
//	}

//	fun endLogin(data: Intent) {
//		try {
//			val authorizationResponse = appAuth.getAuthorizationResponse(
//				AuthorizationResponse.fromIntent(data),
//				AuthorizationException.fromIntent(data)
//			)
//
//			var tokenResponse: TokenResponse?
//
//			CoroutineScope(Dispatchers.IO).launch {
//				try {
//					tokenResponse = appAuth.redeemCodeForToken(authorizationResponse)
//					withContext(Dispatchers.Main) {
//						tokenResponse?.let {
//							this@MainActivityViewModel.state.saveTokens(it)
//							this@MainActivityViewModel.loginCompleted.postValue(Event(true))
//						}
//					}
//				} catch (exception: ApplicationException) {
//					withContext(Dispatchers.Main) {
//						Toast.makeText(this@MainActivityViewModel.app.applicationContext, "Token storage error!", Toast.LENGTH_SHORT).show()
//					}
//				}
//			}
//		} catch (exception: ApplicationException) {
//			Toast.makeText(this@MainActivityViewModel.app.applicationContext, "Login Finish Error!", Toast.LENGTH_SHORT).show()
//		}
//	}
}
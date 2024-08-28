package com.patrickr.authapp.authentication

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.patrickr.authapp.authentication.biometric.BiometricHandler
import com.patrickr.authapp.authentication.biometric.BiometricHandlerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import net.openid.appauth.TokenResponse
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface AuthenticationHandlerListener {
	fun loginStarted(intent: Intent)
	fun loginFinished(success: Boolean)
}

class AuthenticationHandler(
	private val activity: AppCompatActivity,
): BiometricHandlerListener {
	private val config: AuthenticationConfig = AuthenticationConfig.load(context)
	private val state: AuthenticationStateManager = AuthenticationStateManager()
	private val context: Context
		get() {
			return activity.applicationContext
		}
	private var authenticationService = AuthorizationService(context)
	private val biometricHandler = BiometricHandler(activity, this)
	var listener: AuthenticationHandlerListener? = null

	fun fetchMetadata(): AuthorizationServiceConfiguration {
		return AuthorizationServiceConfiguration(this.config.getAuthorizationUri(), this.config.getTokenUri())
	}

	fun getAuthorizationRedirectIntent(metadata: AuthorizationServiceConfiguration, biometric: Boolean = false): Intent {
		Log.d("AuthenticationHandler", "getAuthorizationRedirectIntent() called.")
		val prompt = if(biometric) {
			"none"
		} else {
			"login"
		}
		val request = AuthorizationRequest.Builder(
			metadata,
			this.config.clientId,
			ResponseTypeValues.CODE,
			config.getLoginRedirecUri()
		).setScope(this.config.scope)
			.setPrompt(prompt)
			.build()
		Log.d(ContentValues.TAG, request.jsonSerializeString())
		Log.d("AuthenticationHandler", "Request built.")
		val intent = authenticationService.getAuthorizationRequestIntent(request)
		return intent
	}

	fun getAuthorizationResponse(
		response: AuthorizationResponse?,
		exception: AuthorizationException?
	): AuthorizationResponse {
		Log.d("AuthenticationHandler", "getAuthorizationResponse() called.")
		exception?.let {
			Log.d("AuthenticationHandler", "getAuthorizationResponse() found an exception.")
			throw createAuthorizationError("Authorization Request Error", it)
		}

		require(response != null)
		Log.d("AuthenticationHandler", "getAuthorizationResponse() returning response.")
		return response
	}

	suspend fun redeemCodeForToken(authResponse: AuthorizationResponse): TokenResponse? {
		return suspendCoroutine { continuation ->
			val extraParams = mutableMapOf<String, String>()
			val tokenRequest = authResponse.createTokenExchangeRequest(extraParams)

			authenticationService.performTokenRequest(tokenRequest) { tokenResponse, exception ->
				tokenResponse?.let {
					Log.i(ContentValues.TAG, "Authorization code grant response received successfully")
					Log.d(
						ContentValues.TAG,
						"AT: ${tokenResponse.accessToken}, RT: ${tokenResponse.refreshToken}, IDT: ${tokenResponse.idToken}"
					)
					continuation.resume(tokenResponse)
				}

				exception?.let {
					val error = createAuthorizationError("Authorization Response Error", it)
					continuation.resumeWithException(error)
				}
			}
		}
	}

	suspend fun refreshAccessToken(
		metadata: AuthorizationServiceConfiguration,
		refreshToken: String
	): TokenResponse? {

		return suspendCoroutine { continuation ->
			val tokenRequest = TokenRequest.Builder(
				metadata,
				this.config.clientId
			)
				.setGrantType(GrantTypeValues.REFRESH_TOKEN)
				.setRefreshToken(refreshToken)
				.build()

			authenticationService.performTokenRequest(tokenRequest) { tokenResponse, exception ->
				tokenResponse?.let {
					Log.i(ContentValues.TAG, "Refresh token grant response received successfully")
					continuation.resume(tokenResponse)
				}

				exception?.let {
					val error = createAuthorizationError("Token Refresh Error", it)
					continuation.resumeWithException(error)
				}
			}
		}
	}

	// nebude trebalo
	fun getEndSessionRedirectIntent(
		metadata: AuthorizationServiceConfiguration,
		idToken: String?
	): Intent {
		val request = EndSessionRequest.Builder(metadata)
			.setIdTokenHint(idToken)
			.build()

		return authenticationService.getEndSessionRequestIntent(request)
	}

	fun dispose() {
		this.authenticationService.dispose()
	}

	private fun createAuthorizationError(
		title: String,
		exception: AuthorizationException?
	): ServerCommunicationException {

		val parts = mutableListOf<String>()

		exception?.let {
			parts.add("(${it.type} / ${it.code})")

		}
		exception?.error?.let {
			parts.add(it)
		}

		val description: String = if (exception?.errorDescription != null) {
			exception.errorDescription!!
		} else {
			GENERIC_ERROR
		}
		parts.add(description)

		val fullDescription = parts.joinToString(" : ")
		Log.e(ContentValues.TAG, fullDescription)
		return ServerCommunicationException(title, fullDescription)
	}

	fun startLogin(biometric: Boolean = false) {
		var metadata = this.state.metadata

		CoroutineScope(Dispatchers.IO).launch {
			try {
				if (metadata == null) {
					metadata = fetchMetadata()
				}

				withContext(Dispatchers.Main) {
					this@AuthenticationHandler.state.metadata = metadata
					metadata?.let {
						val intent = getAuthorizationRedirectIntent(it, biometric)
						this@AuthenticationHandler.listener?.loginStarted(intent)
					}
				}
			} catch (exception: ApplicationException) {
				withContext(Dispatchers.Main) {
					Toast.makeText(this@AuthenticationHandler.context, "Login Error!", Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	fun endLogin(data: Intent) {
		try {
			val authorizationResponse = getAuthorizationResponse(
				AuthorizationResponse.fromIntent(data),
				AuthorizationException.fromIntent(data)
			)

			var tokenResponse: TokenResponse?

			CoroutineScope(Dispatchers.IO).launch {
				try {
					tokenResponse = redeemCodeForToken(authorizationResponse)
					withContext(Dispatchers.Main) {
						tokenResponse?.let {
							this@AuthenticationHandler.state.saveTokens(it)
							this@AuthenticationHandler.listener?.loginFinished(true)
						}
					}
				} catch (exception: ApplicationException) {
					withContext(Dispatchers.Main) {
						Toast.makeText(this@AuthenticationHandler.context, "Token storage error!", Toast.LENGTH_SHORT).show()
					}
				}
			}
		} catch (exception: ApplicationException) {
			Toast.makeText(this@AuthenticationHandler.context, "Login Finish Error!", Toast.LENGTH_SHORT).show()
		}
	}

	fun startBiometricLogin() {
		biometricHandler.startBiometricAuthentication()
	}

	override fun biometrcLoginFinished(success: Boolean, message: String?) {
		if (success) {
			startLogin(true)
		}
	}
}
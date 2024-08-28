package com.patrickr.authapp.authentication.biometric

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface BiometricHandlerListener {
	fun biometrcLoginFinished(success: Boolean, message: String?)
}

class BiometricError(
	error: BiometricErrorCase,
	additionalInfo: String? = null
): Throwable(error.message + additionalInfo)

enum class BiometricErrorCase {
	authenticationFailed,
	authenticationError;

	val message: String
		get() {
			when(this) {
				BiometricErrorCase.authenticationError -> {
					return "Authentication Failed."
				}

				BiometricErrorCase.authenticationFailed -> {
					return "Authentication Error."
				}
			}
		}
}

class BiometricHandler(
	private val activity: AppCompatActivity,
	private val listener: BiometricHandlerListener
) {

	private val context: Context
		get() {
			return activity.applicationContext
		}

	private fun isBiometryAvailable(): Boolean {
		val biometricManager = BiometricManager.from(context)
		val authenticator = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
		return authenticator == BiometricManager.BIOMETRIC_SUCCESS

	}

	@Throws
	suspend fun startBiometricPrompt(): BiometricPrompt.AuthenticationResult {
		return suspendCancellableCoroutine<BiometricPrompt.AuthenticationResult> { continuation ->
			val executor = ContextCompat.getMainExecutor(context)
			val callback = object: BiometricPrompt.AuthenticationCallback() {
				override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
					super.onAuthenticationSucceeded(result)
					continuation.resume(result)
				}

				override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
					super.onAuthenticationError(errorCode, errString)
					continuation.resumeWithException(BiometricError(BiometricErrorCase.authenticationError, errString.toString()))
				}

				override fun onAuthenticationFailed() {
					super.onAuthenticationFailed()
					continuation.resumeWithException(BiometricError(BiometricErrorCase.authenticationFailed))
				}
			}

			val prompt = BiometricPrompt(this.activity, executor, callback)
			val promptInfo = createBiometricPromptInfo()
			prompt.authenticate(promptInfo)
		}
	}

	private fun createBiometricPromptInfo(): BiometricPrompt.PromptInfo {
		return BiometricPrompt.PromptInfo
			.Builder()
			.setTitle("Biometric Login")
			.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
			.build()
	}

	fun startBiometricAuthentication() {
		if(!isBiometryAvailable()) {
			Log.e("Biometric", "Biometry not available.")
			return
		}

		CoroutineScope(Dispatchers.Default).async {
			try {
				val biometryPrompt = async { startBiometricPrompt() }.await()
				listener.biometrcLoginFinished(true, null)
			} catch (exception: Throwable) {
				listener.biometrcLoginFinished(false, exception.message)
				Log.e("Biometric",  "Error occured: ${exception.message}")
			}
		}
	}
}
package com.patrickr.authapp.authentication

import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.TokenResponse

class AuthenticationStateManager {
	private var authState: AuthState? = null
	var idToken: String? = null

	var metadata: AuthorizationServiceConfiguration?
		get () {
			return this.authState?.authorizationServiceConfiguration
		}
		set (configuration) {
			configuration?.let {
				this.authState = AuthState(it)
			}
		}

	val tokenResponse: TokenResponse?
		get () {
			return this.authState?.lastTokenResponse
		}

	fun saveTokens(tokenResponse: TokenResponse) {
		if(tokenResponse.idToken != null) {
			this.idToken = tokenResponse.idToken
		}

		this.authState?.update(tokenResponse, null)
	}

	fun clearTokens() {
		val metadata = this.authState?.authorizationServiceConfiguration
		metadata?.let {
			this.authState = AuthState(metadata)
			this.idToken = null
		}
	}
}
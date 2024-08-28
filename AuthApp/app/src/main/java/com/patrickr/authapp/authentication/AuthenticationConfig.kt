package com.patrickr.authapp.authentication

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.patrickr.authapp.R
import okio.Buffer
import okio.buffer
import okio.source
import java.nio.charset.Charset

class AuthenticationConfig {
	@SerializedName("client_id") lateinit var clientId: String
	@SerializedName("redirect_uris") lateinit var redirectUris: Array<String>
	@SerializedName("response_type") lateinit var responseType: String
	@SerializedName("authorize_uri") lateinit var authorizeUri: String
	@SerializedName("redirect_uri_login") lateinit var redirectUri: String
	@SerializedName("redirect_uri_logout") lateinit var logoutUri: String
	@SerializedName("redirect_uri_refresh") lateinit var refreshRedirectUri: String
	@SerializedName("token_uri") lateinit var tokenUri: String
	@SerializedName("use_pkce") var usePkce: Boolean = false

	lateinit var scope: String

	fun getAuthorizationUri(): Uri {
		return Uri.parse(authorizeUri)
	}

	fun getRedirectUris(): MutableList<Uri> {
		val redirects = mutableListOf<Uri>()
		for (stringUri in redirectUris) {
			redirects.add(Uri.parse(stringUri))
		}

		return redirects
	}

	fun getRedirectUrisAsString(): String {
		return "[$redirectUri, $refreshRedirectUri, $logoutUri]"
	}

	fun getLoginRedirecUri():Uri {
		return Uri.parse(redirectUri)
	}

	fun getLogoutUri(): Uri {
		return Uri.parse(logoutUri)
	}

	fun getRefreshUri(): Uri {
		return Uri.parse(refreshRedirectUri)
	}

	fun getTokenUri(): Uri {
		return Uri.parse(tokenUri)
	}


	companion object {
		fun load(context: Context): AuthenticationConfig {
			val config = context.resources.openRawResource(R.raw.authentication_resource)
			val configSource = config.source().buffer()
			val configBuffer = Buffer()
			configSource.readAll(configBuffer)
			val configJson = configBuffer.readString(Charset.forName("UTF-8"))
			return Gson().fromJson(configJson, AuthenticationConfig::class.java)
		}
	}
}
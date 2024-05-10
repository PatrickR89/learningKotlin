package com.patrickr.top10apps

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import java.net.URL
import com.patrickr.top10apps.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class FeedEntry {
	var name: String = ""
	var artist: String = ""
	var releaseDate: String = ""
	var summary: String = ""
	var imageURL: String = ""

	override fun toString(): String {
		return """
			name = $name
			artist = $artist
			releaseDate = $releaseDate
			imageURL = $imageURL
		""".trimIndent()
	}
}

class MainActivity : AppCompatActivity() {
	private val TAG = "MainActivity"
	private lateinit var activityMain: ActivityMainBinding
//	private val downloadData by lazy { DownloadData(this, activityMain.xmlListView) }
	private var downloadData: DownloadData? = null
	private var feedUrl: String = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
	private var limit: Int = 10
	private var feedCachedUrl = "INVALIDATED"
	private val STATE_URL = "feedUrl"
	private val STATE_LIMIT = "feedLimit"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityMain = ActivityMainBinding.inflate(layoutInflater)
		if (savedInstanceState != null) {
			feedUrl = savedInstanceState.getString(STATE_URL)!!
			limit = savedInstanceState.getInt(STATE_LIMIT)
		}
		setContentView(activityMain.root)
		downloadData = DownloadData(this, activityMain.xmlListView)
		downloadData?.execute(feedUrl.format(limit)
		)
		downloadData = null
	}

	override fun onDestroy() {
		super.onDestroy()
		downloadData?.cancel(true)
		downloadData = null
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		super.onCreateOptionsMenu(menu)
		Log.d(TAG, "Creating menu from: $menu")
		menuInflater.inflate(R.menu.feeds_menu, menu)
		if (limit == 10) {
			menu?.findItem(R.id.menu10)?.isChecked = true
		} else {
			menu?.findItem(R.id.menu25)?.isChecked = true
		}
		return true
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putString(STATE_URL, feedUrl)
		outState.putInt(STATE_LIMIT, limit)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		 when (item.itemId) {
			R.id.buttonFree ->
				feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"

			R.id.buttonPaid ->
				feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"

			R.id.buttonSongs ->
				feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"

			R.id.menu10, R.id.menu25 -> {
				if (!item.isChecked) {
					item.isChecked = true
					limit = 35 - limit
					Log.d(TAG, "")
				}
			}
			 R.id.menuRefresh -> feedCachedUrl = "INVALIDATED"
			else ->
				return super.onOptionsItemSelected(item)
		}
		downloadUrl(feedUrl.format(limit))
		return true
	}

	private fun downloadUrl(feedUrl: String) {
		Log.d(TAG, "downloadUrl(feedUrl:) start async task")
		if (feedUrl != feedCachedUrl) {
			downloadData = DownloadData(this, activityMain.xmlListView)
			downloadData?.execute(feedUrl)
			feedCachedUrl = feedUrl
			downloadData = null
		} else {
			Log.d(TAG, "URL remains the same.")
		}
		Log.d(TAG, "downloadUrl(feedUrl:) done")
	}

	companion object {
		private class DownloadData(context: Context, listView: ListView) : AsyncTask<String, Void, String>() {
			private val TAG = "DownloadData"
			var propContext: Context by Delegates.notNull()
			var propListView: ListView by Delegates.notNull()

			init {
				propContext = context
				propListView = listView
			}

			override fun doInBackground(vararg params: String?): String {
				val param = params[0] ?: return ""
				val rssFeed = downloadXML(param)
				if (rssFeed.isEmpty()) {
					Log.e(TAG, "doInBackground: Error downloading.")
				}
				return rssFeed
			}

			override fun onPostExecute(result: String?) {
				super.onPostExecute(result)
				val parseApplications = ParseApplications()
				if (result != null) {
					parseApplications.parse(result)
				}

				val feedAdapter = FeedAdapter(propContext, R.layout.list_record, parseApplications.applications)
				propListView.adapter = feedAdapter
			}

			private fun downloadXML(urlPath: String): String {
				return URL(urlPath).readText()
			}
		}
	}
}
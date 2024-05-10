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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityMain = ActivityMainBinding.inflate(layoutInflater)

		setContentView(activityMain.root)
		downloadData = DownloadData(this, activityMain.xmlListView)
		downloadData?.execute(
			"http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"
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
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val feedUrl: String
		feedUrl = when (item.itemId) {
			R.id.buttonFree ->
				"http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"

			R.id.buttonPaid ->
				"http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=10/xml"

			R.id.buttonSongs ->
				"http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml"

			else ->
				return super.onOptionsItemSelected(item)
		}
		downloadUrl(feedUrl)
		return true
	}

	private fun downloadUrl(feedUrl: String) {
		Log.d(TAG, "downloadUrl(feedUrl:) start async task")
		downloadData = DownloadData(this, activityMain.xmlListView)
		downloadData?.execute(feedUrl)
		downloadData = null
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
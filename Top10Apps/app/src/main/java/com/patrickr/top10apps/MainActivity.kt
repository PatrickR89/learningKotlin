package com.patrickr.top10apps

import android.app.LauncherActivity.ListItem
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import java.net.URL
import com.patrickr.top10apps.databinding.ActivityMainBinding
import com.patrickr.top10apps.databinding.ListItemBinding
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
	private val downloadData by lazy { DownloadData(this, activityMain.xmlListView) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityMain = ActivityMainBinding.inflate(layoutInflater)
		setContentView(activityMain.root)
		Log.d(TAG, "onCreate called")
		downloadData.execute(
			"http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"
		)
		Log.d(TAG, "onCreate done")
	}

	override fun onDestroy() {
		super.onDestroy()
		downloadData.cancel(true)
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
				Log.d(TAG, "doInBackground: parameter is ${params[0]}")
				val param = params[0] ?: return ""
				val rssFeed = downloadXML(param)
				if (rssFeed.isEmpty()) {
					Log.e(TAG, "doInBackground: Error downloading.")
				}
				return rssFeed
			}

			override fun onPostExecute(result: String?) {
				super.onPostExecute(result)
				Log.d(TAG, "onPostExecute: parameter os $result")
				val parseApplications = ParseApplications()
				if (result != null) {
					parseApplications.parse(result)
				}
//				val arrayAdapter = ArrayAdapter<FeedEntry>(propContext, R.layout.list_item, parseApplications.applications)
//				propListView.adapter = arrayAdapter
				val feedAdapter = FeedAdapter(propContext, R.layout.list_record, parseApplications.applications)
				propListView.adapter = feedAdapter
			}

			private fun downloadXML(urlPath: String): String {
				return URL(urlPath).readText()
			}


		}
	}
}
package com.patrickr.top10apps

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

class ParseApplications {
	private val TAG = "ParseApplications"
	val applications = ArrayList<FeedEntry>()

	fun parse(xmlData: String): Boolean {
		var status = true
		var inEntry = false
		var textValue = ""

		try {
			val factory = XmlPullParserFactory.newInstance()
			factory.isNamespaceAware = true
			val xpp = factory.newPullParser()
			xpp.setInput(xmlData.reader())
			var eventType = xpp.eventType
			var currentRecord = FeedEntry()
			while (eventType != XmlPullParser.END_DOCUMENT) {
				val tagName = xpp.name?.lowercase()
				when (eventType) {
					XmlPullParser.START_TAG ->  {
						if (tagName == "entry") {
							inEntry = true
						}
					}

					XmlPullParser.TEXT -> textValue = xpp.text
					XmlPullParser.END_TAG -> {
						if (inEntry) {
							when (tagName) {
								"entry" -> {
									applications.add(currentRecord)
									inEntry = false
									currentRecord = FeedEntry()
								}

								"name" -> currentRecord.name = textValue
								"artist" -> currentRecord.artist = textValue
								"releasedate" -> currentRecord.releaseDate = textValue
								"summary" -> currentRecord.summary = textValue
								"image" -> currentRecord.imageURL = textValue
							}
						}
					}
				}

				eventType = xpp.next()
			}
		} catch (error: Exception) {
			error.printStackTrace()
			status = false
		}
		return status
	}
}
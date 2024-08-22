package com.patrickr.contactsapp.models

import android.database.Cursor

data class Contact(val id: Int, var name: String, var phoneNumber: String) {
	constructor(name: String, phoneNumber: String): this(-1, name, phoneNumber)
	constructor(cursor: Cursor): this(
		cursor.getString(0).toInt(),
		cursor.getString(1),
		cursor.getString(2)
	)
}

package com.patrickr.contactsapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.patrickr.contactsapp.models.Contact
import com.patrickr.contactsapp.utils.Constants

class DatabaseHandler(
	context: Context
	): SQLiteOpenHelper(
	context,
	Constants.DATABASE_NAME,
	null,
	Constants.DATABASE_VERSION
) {
	override fun onCreate(p0: SQLiteDatabase?) {
		val tableQuery = """CREATE TABLE ${Constants.TABLE_NAME} (
				${Constants.KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
				${Constants.KEY_NAME} TEXT,
				${Constants.KEY_PHONE} TEXT)"""

		p0?.execSQL(tableQuery)
	}

	override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
		p0?.execSQL("DROP TABLE IF EXISTS ${Constants.TABLE_NAME}")

		onCreate(p0)
	}

	fun addContact(contact: Contact) {
		val db = this.writableDatabase

		val values = ContentValues()
		values.put(Constants.KEY_NAME, contact.name)
		values.put(Constants.KEY_PHONE, contact.phoneNumber)

		db.insert(Constants.TABLE_NAME, null, values)
		db.close()
	}

	fun getContact(id: Int): Contact {
		val db = this.readableDatabase
		val cursor = db.query(
			Constants.TABLE_NAME,
			arrayOf(
				Constants.KEY_ID,
				Constants.KEY_NAME,
				Constants.KEY_PHONE
			),
			Constants.KEY_ID + "=?",
			arrayOf(id.toString()),
			null,
			null,
			null,
			null
		)

		cursor?.let {
			it.moveToFirst()
		}

		return Contact(cursor)
	}

	fun getAllContacts(): MutableList<Contact> {
		val db = this.readableDatabase

		val contactList = mutableListOf<Contact>()

		val selectAll = "SELECT * FROM ${Constants.TABLE_NAME}"
		val cursor = db.rawQuery(selectAll, null)

		if(cursor.moveToFirst()) {
			do {
				val contact = Contact(cursor)

				contactList.add(contact)
			} while(cursor.moveToNext())
		}

		return contactList
	}

	fun updateContact(contact: Contact): Int {
		val db = writableDatabase
		val values = ContentValues()
		values.put(Constants.KEY_NAME, contact.name)
		values.put(Constants.KEY_PHONE, contact.phoneNumber)


		return db.update(
			Constants.TABLE_NAME,
			values,
			Constants.KEY_ID + "=?",
			arrayOf(contact.id.toString()))
	}

	fun deleteContact(contact: Contact) {
		val db = writableDatabase
		db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", arrayOf(contact.id.toString()))
	}

	fun getContactsCount(): Int {
		val countQuery = "SELECT * FROM ${Constants.TABLE_NAME}"
		val db = readableDatabase
		val cursor = db.rawQuery(countQuery, null)
		return cursor.count
	}
}
package com.patrickr.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.patrickr.contactsapp.data.DatabaseHandler
import com.patrickr.contactsapp.models.Contact

class MainActivity : AppCompatActivity() {
	private lateinit var textView: TextView
	val databaseHandler = DatabaseHandler(this)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
//		addContact()
		findTextView()
//		getContacts()
//		val contact = getContactById(7)
//		updateContact(contact)
//		databaseHandler.deleteContact(contact)
		getContacts()
	}

	fun addContact() {
		databaseHandler.addContact(Contact("Marco", "+35458876"))
		databaseHandler.addContact(Contact("Steven", "+98745654312"))
		databaseHandler.addContact(Contact("John", "+66644488"))
		databaseHandler.addContact(Contact("Maria", "+6578221312"))
		databaseHandler.addContact(Contact("Anna", "+8854213456"))
	}

	fun findTextView() {
		textView = findViewById(R.id.text_view_data)
	}

	fun getContacts() {
		val contactList = databaseHandler.getAllContacts()
		for (contact in contactList) {
			textView.append("\nID: ${contact.id} Name: ${contact.name} - Phone: ${contact.phoneNumber}")
		}

		textView.append("\n\nAll contacts count: ${databaseHandler.getContactsCount()}")
	}

	fun getContactById(id: Int): Contact {
		val contact = databaseHandler.getContact(id)
		textView.text = "Name: ${contact.name} - Phone: ${contact.phoneNumber}"
		return contact
	}

	fun updateContact(contact: Contact) {
		val newContact = contact
		newContact.name = "Margot"
		newContact.phoneNumber = "+99999999999"
		databaseHandler.updateContact(newContact)
		val loadedContact = getContactById(contact.id)
	}
}
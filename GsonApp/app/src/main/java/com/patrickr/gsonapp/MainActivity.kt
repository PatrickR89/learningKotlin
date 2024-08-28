package com.patrickr.gsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val gson = Gson()
		val address = Address("Romania", "Buka")
		val family = arrayOf(
			FamilyMember("Father", 10063),
			FamilyMember("Sister", 22)
		)
		val employee = Employee("ALex", 22, "test@test.com", address, family)
		val serEmployee = SerializableEmployee("ALex", 22, "test@test.com", address, family)
		val json = gson.toJson(employee)
		Log.d("jsonTo", json)
		val newJson =
			"{\"address\":{\"city\":\"Buka\",\"country\":\"Romania\"},\"age\":22,\"familyMembers\":[{\"age\":10063,\"role\":\"Father\"},{\"age\":22,\"role\":\"Sister\"}],\"firstName\":\"ALex\",\"mail\":\"test@test.com\"}"
		val newEmployee = gson.fromJson(newJson, Employee::class.java)
		Log.d("jsonFrom", employee.toString())
		val familyJson = "[{\"age\":10063,\"role\":\"Father\"},{\"age\":22,\"role\":\"Sister\"}]"


		val familyType: Type = object: TypeToken<MutableList<FamilyMember>>(){}.type

		val familyMembers: MutableList<FamilyMember> = gson.fromJson(familyJson, familyType)
		Log.d("jsonFamily", familyMembers.toString())
	}

	fun serializeExposed() {

	}
}

/*
To create array type in fetching JSON data, use:

val familyType: Type = object : TypeToken<MutableList<FamilyMember>>().type
 */
package com.patrickr.gsonapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Employee(
	@Expose(serialize = false) @SerializedName("firstName") val firstName: String,
	val age: Int,
	val mail: String,
	@Expose(deserialize = false) val address: Address,
	val familyMembers: Array<FamilyMember>
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Employee

		if (firstName != other.firstName) return false
		if (age != other.age) return false
		if (mail != other.mail) return false
		if (address != other.address) return false
		if (!familyMembers.contentEquals(other.familyMembers)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = firstName.hashCode()
		result = 31 * result + age
		result = 31 * result + mail.hashCode()
		result = 31 * result + address.hashCode()
		result = 31 * result + familyMembers.contentHashCode()
		return result
	}
}

data class  SerializableEmployee(
	val firstName: String,
	val age: Int,
	val mail: String,
	val address: Address,
	val familyMembers: Array<FamilyMember>
): Serializable

data class Address(
	val country: String,
	val city: String
)
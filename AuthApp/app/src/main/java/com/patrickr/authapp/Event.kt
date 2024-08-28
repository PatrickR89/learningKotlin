package com.patrickr.authapp

class Event<T>(private val data: T) {
	var handled = false
			private set

	fun getData(): T? {
		return if (handled) {
			null
		} else {
			handled = true
			data
		}
	}
}
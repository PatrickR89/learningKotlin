package com.patrickr.composetutorial

sealed class Screen(val route: String) {
	class MainScreen: Screen("MainScreen")
	class DetailScreen: Screen("DetailsScreen")

	fun withArgs(vararg args: String): String {
		return buildString {
			append(route)
			args.forEach { arg ->
				append("/$arg")
			}
		}
	}
}
package com.dicoding.rotie.presentation.ui.navigation

sealed class Screen(val route: String) {
	object Home : Screen("home")
	object Cart : Screen("cart")
	object Profile : Screen("profile")
	object DetailBread : Screen("home/{breadId}") {
		fun createRoute(breadId: Long) = "home/$breadId"
	}
	object Login : Screen("login")
}

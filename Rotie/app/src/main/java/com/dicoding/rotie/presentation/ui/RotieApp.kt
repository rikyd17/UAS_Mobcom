package com.dicoding.rotie.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.rotie.R
import com.dicoding.rotie.presentation.ui.navigation.NavigationItem
import com.dicoding.rotie.presentation.ui.navigation.Screen
import com.dicoding.rotie.presentation.ui.screen.about.ProfileScreen
import com.dicoding.rotie.presentation.ui.screen.cart.CartScreen
import com.dicoding.rotie.presentation.ui.screen.detail.DetailScreen
import com.dicoding.rotie.presentation.ui.screen.home.HomeScreen
import com.dicoding.rotie.presentation.ui.screen.login.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RotieApp(
	modifier: Modifier = Modifier,
	navController: NavHostController = rememberNavController(),
) {
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry?.destination?.route

	Scaffold(
		bottomBar = {
			when (currentRoute) {
				Screen.DetailBread.route -> {}
				Screen.Login.route -> {}
				else -> BottomBar(navController)
			}
		},
		modifier = modifier
	) { innerPadding ->
		NavHost(
			navController = navController,
			startDestination = Screen.Login.route,
			modifier = Modifier.padding(innerPadding)
		) {
			composable(Screen.Home.route) {
				HomeScreen(
					navigateToDetail = { breadId ->
						navController.navigate(Screen.DetailBread.createRoute(breadId))
					}
				)
			}
			composable(Screen.Cart.route) {
				CartScreen()
			}
			composable(Screen.Profile.route) {
				ProfileScreen(
					navigateToLogin = {
						navController.popBackStack()
						navController.navigate(Screen.Login.route)
					}
				)
			}
			composable(
				route = Screen.DetailBread.route,
				arguments = listOf(navArgument("breadId") { type = NavType.LongType })
			) {
				val id = it.arguments?.getLong("breadId") ?: -1L
				DetailScreen(
					breadId = id,
					navigateBack = { navController.navigateUp() },
					navigateToCart = {
						navController.popBackStack()
						navController.navigate(Screen.Cart.route) {
							popUpTo(navController.graph.findStartDestination().id) {
								saveState = true
							}
							launchSingleTop = true
							restoreState = true
						}
					}
				)
			}
			composable(Screen.Login.route) {
				LoginScreen(
					navigateToHome = { navController.navigate(Screen.Home.route) }
				)
			}
		}

	}
}

@Composable
private fun BottomBar(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry?.destination?.route
	val navigationItems = listOf(
		NavigationItem(
			title = stringResource(R.string.menu_home),
			icon = Icons.Default.Home,
			screen = Screen.Home
		),
		NavigationItem(
			title = stringResource(R.string.menu_cart),
			icon = Icons.Default.ShoppingCart,
			screen = Screen.Cart
		),
		NavigationItem(
			title = stringResource(R.string.menu_profile),
			icon = Icons.Default.AccountCircle,
			screen = Screen.Profile
		),
	)
	NavigationBar(
		modifier = modifier
	) {
		navigationItems.forEach { item ->
			NavigationBarItem(
				selected = currentRoute == item.screen.route,
				label = { Text(text = item.title) },
				icon = {
					Icon(
						imageVector = item.icon,
						contentDescription = item.title
					)
				},
				onClick = {
					navController.navigate(item.screen.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = true
						}
						restoreState = true
						launchSingleTop = true
					}
				},
			)
		}
	}

}
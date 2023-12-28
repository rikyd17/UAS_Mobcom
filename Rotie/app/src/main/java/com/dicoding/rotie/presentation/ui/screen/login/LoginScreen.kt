package com.dicoding.rotie.presentation.ui.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dicoding.rotie.presentation.ui.common.UiState
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
	viewModel: LoginViewModel = koinViewModel(),
	navigateToHome: () -> Unit,
) {
	Log.d("LoginScreen", viewModel.session ?: "no token")

	viewModel.loginState.collectAsState().value.let { loginState ->
		LoginContent(
			composeAuth = viewModel.composeAuth,
			isLoginSuccess = loginState,
			navigateToHome = navigateToHome,
			setUser = { viewModel.setUser() }
		)
	}


}

@Composable
fun LoginContent(
	composeAuth: ComposeAuth,
	isLoginSuccess: UiState<Unit>,
	navigateToHome: () -> Unit,
	setUser: () -> Unit
) {
	LaunchedEffect(isLoginSuccess) {
		if (isLoginSuccess is UiState.Success) {
			navigateToHome()
		}
	}

	val authState = composeAuth.rememberSignInWithGoogle(
		onResult = {
			when (it) {
				NativeSignInResult.ClosedByUser -> {}
				is NativeSignInResult.Error -> {
					Log.e("LoginScreen", it.toString())
				}
				is NativeSignInResult.NetworkError -> {
					Log.e("LoginScreen", it.toString())
				}
				NativeSignInResult.Success -> setUser()
			}
		}
	)
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Button(onClick = { authState.startFlow() }) {
			Text("Sign in with Google")
		}
	}
}
package com.dicoding.rotie.presentation.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rotie.presentation.ui.common.UiState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.supabaseJson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
	private val supabase: SupabaseClient
) : ViewModel() {

	val session = supabase.auth.currentAccessTokenOrNull()

	private val _loginState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Loading)
	val loginState: StateFlow<UiState<Unit>>
		get() = _loginState

	val composeAuth = supabase.composeAuth
	fun setUser() {
		_loginState.value = UiState.Success(Unit)
	}
}
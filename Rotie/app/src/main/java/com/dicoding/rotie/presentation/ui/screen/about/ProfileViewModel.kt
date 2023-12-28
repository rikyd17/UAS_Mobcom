package com.dicoding.rotie.presentation.ui.screen.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rotie.domain.model.UserProfile
import com.dicoding.rotie.domain.repository.AuthRepository
import com.dicoding.rotie.presentation.ui.common.UiState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composeAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.PrimitiveIterator

class ProfileViewModel(
	private val supabase: SupabaseClient,
	private val repository: AuthRepository
) : ViewModel() {
	val composeAuth = supabase.composeAuth

	private val _uiState: MutableStateFlow<UiState<UserProfile>> = MutableStateFlow(UiState.Loading)
	val uiState: StateFlow<UiState<UserProfile>>
		get() = _uiState

	fun getProfile() {
		viewModelScope.launch {
			repository.getProfile()
				.collect { profile ->
					_uiState.value = UiState.Success(profile)
				}
		}
	}
}
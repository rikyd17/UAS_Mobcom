package com.dicoding.rotie.presentation.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rotie.domain.model.Bread
import com.dicoding.rotie.domain.repository.BreadRepository
import com.dicoding.rotie.presentation.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
	private val repository: BreadRepository
) : ViewModel() {
	private val _uiState: MutableStateFlow<UiState<List<Bread>>> = MutableStateFlow(UiState.Loading)
	val uiState: StateFlow<UiState<List<Bread>>>
		get() = _uiState

	fun getAllBreads() {
		viewModelScope.launch {
			repository.getAllBreads()
				.catch {
					_uiState.value = UiState.Error(it.message.toString())
				}
				.collect { breads ->
					_uiState.value = UiState.Success(breads)
				}
		}
	}

	fun searchBread(query: String) {
		viewModelScope.launch {
			repository.searchBread(query.lowercase())
				.catch {
					_uiState.value = UiState.Error(it.message.toString())
				}
				.collect { breads ->
					_uiState.value = UiState.Success(breads)
				}
		}
	}
}
package com.dicoding.rotie.presentation.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rotie.domain.model.Bread
import com.dicoding.rotie.domain.model.BreadOrder
import com.dicoding.rotie.domain.model.Order
import com.dicoding.rotie.domain.repository.BreadRepository
import com.dicoding.rotie.domain.repository.OrderRepository
import com.dicoding.rotie.presentation.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.PrimitiveIterator

class DetailViewModel(
	private val breadRepository: BreadRepository, private val orderRepository: OrderRepository
) : ViewModel() {
	private val _uiState: MutableStateFlow<UiState<Bread>> = MutableStateFlow(UiState.Loading)
	val uiState: StateFlow<UiState<Bread>>
		get() = _uiState

	private val _addToCartState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Loading)
	val addToCartState: StateFlow<UiState<Unit>>
		get() = _addToCartState

	fun getBreadById(breadId: Long) {
		viewModelScope.launch {
			_uiState.value = UiState.Loading
			breadRepository.getBreadById(breadId)
				.catch {
					Log.e("getBreadById", it.message.toString())
					_uiState.value = UiState.Error(it.message.toString())
				}
				.collect { bread ->
					_uiState.value = UiState.Success(bread)
				}
		}
	}

	fun addToCart(bread: Bread, count: Int) {
		viewModelScope.launch {
			val newBreadOrder = BreadOrder(bread = bread, count = count)
			_addToCartState.value = UiState.Loading
			orderRepository.addOrder(newBreadOrder)
				.catch {
					Log.e("AddToCart", it.message.toString())
					_addToCartState.value = UiState.Error(it.message.toString())
				}
				.collect {
					_addToCartState.value = UiState.Success(Unit)
				}
		}
	}
}
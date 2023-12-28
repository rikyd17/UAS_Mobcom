package com.dicoding.rotie.presentation.ui.screen.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rotie.domain.repository.BreadRepository
import com.dicoding.rotie.domain.repository.OrderRepository
import com.dicoding.rotie.presentation.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(
	private val orderRepository: OrderRepository
) : ViewModel() {
	private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
	val uiState: StateFlow<UiState<CartState>>
		get() = _uiState

	fun getAddedProductOrders() {
		viewModelScope.launch {
			orderRepository.getOrders()
				.collect { orders ->
					val totalPrice =
						orders.sumOf { it.bread.price * it.count }
					_uiState.value = UiState.Success(CartState(orders, totalPrice))
				}
		}
	}

	fun updateProductOrder(orderId: Long, count: Int) {
		viewModelScope.launch {
			if (count == 0) {
				orderRepository.removeOrder(orderId)
					.catch { Log.e("updateProductOrder", it.message.toString()) }
					.collect()
			} else {
				orderRepository.updateOrder(orderId, count)
					.catch { Log.e("updateProductOrder", it.message.toString()) }
					.collect()
			}
			getAddedProductOrders()
		}
	}
}
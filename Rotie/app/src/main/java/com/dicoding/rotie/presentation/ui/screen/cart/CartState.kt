package com.dicoding.rotie.presentation.ui.screen.cart

import com.dicoding.rotie.domain.model.BreadOrder

data class CartState(
	val order: List<BreadOrder> = listOf(),
	val totalPrice: Int
)
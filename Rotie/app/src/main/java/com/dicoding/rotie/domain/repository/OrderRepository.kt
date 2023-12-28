package com.dicoding.rotie.domain.repository

import com.dicoding.rotie.domain.model.BreadOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
	suspend fun getOrders(): Flow<List<BreadOrder>>
	suspend fun addOrder(order: BreadOrder): Flow<Result<Unit>>
	suspend fun updateOrder(orderId: Long, newCountValue: Int): Flow<Result<Unit>>
	suspend fun removeOrder(orderId: Long): Flow<Result<Unit>>
}
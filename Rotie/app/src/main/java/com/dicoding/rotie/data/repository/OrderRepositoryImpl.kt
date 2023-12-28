package com.dicoding.rotie.data.repository

import android.util.Log
import com.dicoding.rotie.domain.model.BreadOrder
import com.dicoding.rotie.domain.model.Order
import com.dicoding.rotie.domain.repository.OrderRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class OrderRepositoryImpl(
	private val supabase: SupabaseClient
) : OrderRepository {
	override suspend fun getOrders(): Flow<List<BreadOrder>> = flowOf(
		supabase.from("orders").select(
			Columns.raw("id, count, breads (id, name, description, image, price)")
		) {
			filter {
				eq("users_id", supabase.auth.retrieveUserForCurrentSession(updateSession = true).id)
			}
		}.decodeList<BreadOrder>()
	)

	override suspend fun addOrder(order: BreadOrder): Flow<Result<Unit>> = flow {
		val newOrder = Order(
			breadId = order.bread.id,
			count = order.count,
			usersId = supabase.auth.retrieveUserForCurrentSession(updateSession = true).id,
		)
		try {
			supabase.from("orders").insert(newOrder)
			emit(Result.success(Unit))
		} catch (e: RestException) {
			emit(Result.failure(e))
		}
	}

	override suspend fun updateOrder(orderId: Long, newCountValue: Int): Flow<Result<Unit>> = flow {
		try {
			supabase.from("orders").update(
				{
					set("count", newCountValue)
				}
			) {
				filter {
					eq("id", orderId)
				}
			}
			Log.d("updateOrder", "newCount: $newCountValue")
			Log.d("updateOrder", "orderId: $orderId")
			emit(Result.success(Unit))
		} catch (e: RestException) {
			emit(Result.failure(e))
		}
	}

	override suspend fun removeOrder(orderId: Long): Flow<Result<Unit>> = flow {
		try {
			supabase.from("orders").delete {
				filter {
					eq("id", orderId)
				}
			}
			emit(Result.success(Unit))
		} catch (e: RestException) {
			emit(Result.failure(e))
		}
	}
}
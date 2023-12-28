package com.dicoding.rotie.data.repository

import com.dicoding.rotie.domain.model.Bread
import com.dicoding.rotie.domain.repository.BreadRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BreadRepositoryImpl(
	private val supabase: SupabaseClient
) : BreadRepository {

	override suspend fun getAllBreads(): Flow<List<Bread>> =
		flowOf(supabase.from("breads").select().decodeList<Bread>())

	override suspend fun getBreadById(breadId: Long): Flow<Bread> =
		flowOf(supabase.from("breads").select {
			filter {
				eq("id", breadId)
			}
		}.decodeSingle<Bread>())

	override suspend fun searchBread(query: String): Flow<List<Bread>> =
		flowOf(supabase.from("breads").select {
			filter {
				ilike("name", "%$query%")
			}
		}.decodeList<Bread>())

}
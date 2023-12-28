package com.dicoding.rotie.domain.repository

import com.dicoding.rotie.domain.model.Bread
import kotlinx.coroutines.flow.Flow

interface BreadRepository {
	suspend fun getAllBreads(): Flow<List<Bread>>
	suspend fun getBreadById(breadId: Long): Flow<Bread>
	suspend fun searchBread(query: String): Flow<List<Bread>>
}
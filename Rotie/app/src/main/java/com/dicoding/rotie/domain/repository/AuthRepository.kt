package com.dicoding.rotie.domain.repository

import com.dicoding.rotie.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
	suspend fun signin()
	suspend fun signout()
	suspend fun getProfile(): Flow<UserProfile>
}
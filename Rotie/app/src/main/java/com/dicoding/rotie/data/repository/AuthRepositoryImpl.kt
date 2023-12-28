package com.dicoding.rotie.data.repository

import com.dicoding.rotie.domain.model.UserProfile
import com.dicoding.rotie.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.rememberSignOutWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
	private val supabase: SupabaseClient
) : AuthRepository {
	override suspend fun signin() {
	}

	override suspend fun signout() {
	}

	override suspend fun getProfile(): Flow<UserProfile> = flow {
		val user = supabase.auth.retrieveUserForCurrentSession()
		val profile = Json {
			ignoreUnknownKeys = true
		}.decodeFromString<UserProfile>(user.userMetadata.toString())
//		emit(UserProfile(user.userMetadata.toString(), user.email.toString()))
		emit(profile)
	}
}
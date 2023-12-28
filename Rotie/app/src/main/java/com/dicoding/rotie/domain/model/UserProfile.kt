package com.dicoding.rotie.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
	@SerialName("avatar_url")
	val avatarUrl: String,
	val name: String,
	val email: String,
)

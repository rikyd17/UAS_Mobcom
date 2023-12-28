package com.dicoding.rotie.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Bread(
	val id: Long,
	val name: String,
	val description: String,
	val image: String,
	val price: Int,
)
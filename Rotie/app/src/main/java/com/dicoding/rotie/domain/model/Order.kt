package com.dicoding.rotie.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order(
	@SerialName("bread_id")
	val breadId: Long,
	val count: Int,
	@SerialName("users_id")
	val usersId: String,
)

@Serializable
data class BreadOrder(
	val id: Long = 0,
	@SerialName("breads")
	val bread: Bread,
	val count: Int = 0,
)
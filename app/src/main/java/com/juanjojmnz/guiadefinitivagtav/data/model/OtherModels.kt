package com.juanjojmnz.guiadefinitivagtav.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomEvent(
    val id: Int,
    val name: String,
    val reward: String,
    val description: String,
    val imageUrl: String? = null
)
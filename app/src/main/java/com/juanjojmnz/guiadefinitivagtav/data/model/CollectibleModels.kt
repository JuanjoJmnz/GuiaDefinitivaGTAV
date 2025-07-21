package com.juanjojmnz.guiadefinitivagtav.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class SpaceshipPartItem(
    val id: Int,
    val name: String,
    val description: String,

    @SerialName("mapImageUrl")
    val mapImageUrl: String,

    @SerialName("locationImageUrl")
    val locationImageUrl: String,

    @Transient
    val isFound: Boolean = false
)

@Serializable
data class LetterScrapItem(
    val id: Int,
    val name: String,
    val description: String,

    @SerialName("mapImageUrl")
    val mapImageUrl: String,

    @SerialName("locationImageUrl")
    val locationImageUrl: String,

    @Transient
    var isFound: Boolean = false
)
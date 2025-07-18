package com.juanjojmnz.guiadefinitivagtav.data.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class SpaceshipPartItem(
    val id: Int,
    val name: String,
    val description: String,

    @SerialName("mapImageResourceName")
    val mapImageResourceName: String,

    @SerialName("locationImageResourceName")
    val locationImageResourceName: String,

    @Transient
    @DrawableRes val mapImageResId: Int = 0,

    @Transient
    @DrawableRes val locationImageResId: Int = 0,

    @Transient
    val isFound: Boolean = false
)

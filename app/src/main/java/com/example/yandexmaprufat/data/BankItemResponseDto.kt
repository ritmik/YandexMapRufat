package com.example.yandexmaprufat.data

import com.google.gson.annotations.SerializedName

data class BankItemResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lat")
    val lat:Float,
    @SerializedName("lon")
    val lon:Float,
)
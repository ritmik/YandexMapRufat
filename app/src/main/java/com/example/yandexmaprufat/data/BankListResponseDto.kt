package com.example.yandexmaprufat.data

import com.google.gson.annotations.SerializedName

data class BankListResponseDto(

    @SerializedName("id")
    val id: String,
    @SerializedName("list")
    val list: List<Bank>
)
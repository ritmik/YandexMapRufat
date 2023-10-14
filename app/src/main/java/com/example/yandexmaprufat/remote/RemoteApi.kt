package com.example.yandexmaprufat.remote

import com.example.yandexmaprufat.data.Bank
import com.example.yandexmaprufat.data.BankListResponseDto
import com.example.yandexmaprufat.data.Hero
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApi {
    @GET("marvel")
    suspend fun getContacts(): List<Hero>



    @GET("bank/")
    suspend fun getBankList(): Response<List<Bank>>

}
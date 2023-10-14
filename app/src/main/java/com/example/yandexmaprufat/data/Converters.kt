package com.example.yandexmaprufat.data

fun mapFromBankItemResponseDtoToBank(bank: Bank) = Bank(
    id = bank.id.toString(),
    name = bank.name,
     lat=bank.lat,
     lon=bank.lon,


)
fun mapFromBankListResponseDtoToBankList(list: List<Bank>) = list.map {
    mapFromBankItemResponseDtoToBank(it)
}
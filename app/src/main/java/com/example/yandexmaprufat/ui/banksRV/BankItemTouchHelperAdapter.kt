package com.example.yandexmaprufat.ui.banksRV

interface BankItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemDismiss(position: Int)

}
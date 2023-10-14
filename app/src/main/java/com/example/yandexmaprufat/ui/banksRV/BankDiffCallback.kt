package com.example.yandexmaprufat.ui.banksRV

import androidx.recyclerview.widget.DiffUtil
import com.example.yandexmaprufat.data.Bank

class BankDiffCallback : DiffUtil.ItemCallback<Bank>() {

    override fun areItemsTheSame(oldItem: Bank, newItem: Bank) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Bank, newItem: Bank) =
        oldItem == newItem
}
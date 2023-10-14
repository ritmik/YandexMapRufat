package com.example.yandexmaprufat.ui.banksRV

import androidx.recyclerview.widget.RecyclerView
import com.example.yandexmaprufat.data.Bank
import com.example.yandexmaprufat.databinding.ItemBankBinding

class BankViewHolder(val binding: ItemBankBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: Bank) {
        with(binding) {
    textViewName.text = item.name
    textViewDate.text =  "0"

        }
    }
}
package com.example.yandexmaprufat.ui.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexmaprufat.db.MainRepository
import com.example.yandexmaprufat.ui.BankBranchViewModel
import com.example.yandexmaprufat.ui.MainViewModel


class BankBranchViewModelFactory(
   private
val mainRepository: MainRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return BankBranchViewModel(mainRepository) as T
    }
}
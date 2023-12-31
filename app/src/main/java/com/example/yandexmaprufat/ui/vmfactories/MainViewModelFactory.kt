package com.example.yandexmaprufat.ui.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexmaprufat.db.MainRepository
import com.example.yandexmaprufat.ui.MainViewModel


class MainViewModelFactory(
   private
val mainRepository: MainRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return MainViewModel(mainRepository) as T
    }
}
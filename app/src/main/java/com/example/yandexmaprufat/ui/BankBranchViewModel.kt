package com.example.yandexmaprufat.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexmaprufat.data.Bank
import com.example.yandexmaprufat.data.LatestNewsUiState
import com.example.yandexmaprufat.db.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Collections.addAll

class BankBranchViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _bankList: MutableStateFlow<List<Bank>> =
        MutableStateFlow<List<Bank>>(emptyList())

    val bankList: StateFlow<List<Bank>> = _bankList

    init{
        getBankList()
    }

    fun getBankList() {

        viewModelScope.launch {
                _bankList.update {
                    mutableListOf<Bank>().apply {
                        addAll(mainRepository.getBankList().map { noteData ->
                            noteData.copy()
                        })
                    } } }

    }

}
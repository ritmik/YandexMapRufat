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

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<LatestNewsUiState> =
        MutableStateFlow(LatestNewsUiState.Success(emptyList()))
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    private val _bankList: MutableStateFlow<List<Bank>> =
        MutableStateFlow<List<Bank>>(emptyList())

    val bankList: StateFlow<List<Bank>> = _bankList
    init {
        getBankList()
        viewModelScope.launch {
//            launch {
//                delay(2000)
//                _uiState.value = LatestNewsUiState.Success(listOf(1, 2, 3))
//                delay(2000)
//                _uiState.value = LatestNewsUiState.Error(Throwable("ошибка 123334"))
//                _uiState.value = LatestNewsUiState.Success(listOf())
//
//            }
            mainRepository.getUIState()
        }


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
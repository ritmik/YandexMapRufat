package com.example.yandexmaprufat.db


import android.util.Log
import com.example.yandexmaprufat.data.Bank
import com.example.yandexmaprufat.data.BankItemDto
import com.example.yandexmaprufat.data.LatestNewsUiState
import com.example.yandexmaprufat.data.mapFromBankListResponseDtoToBankList
import com.example.yandexmaprufat.remote.Left
import com.example.yandexmaprufat.remote.RemoteApi
import com.example.yandexmaprufat.remote.Right
import com.example.yandexmaprufat.remote.safeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MainRepository(private val remoteApi: RemoteApi /*val dao: UserDao*/) {



     suspend fun getBankList(): List<Bank> {
        return mapFromBankListResponseDtoToBankList(remoteApi.getBankList().body()!!)
    }


    suspend fun getUIState():LatestNewsUiState?{
        var value: LatestNewsUiState? = null//make nullable for testing
        withContext(Dispatchers.IO) {
            when (val r = safeRequest { remoteApi.getContacts() }) {
                is Left -> {
                    value = LatestNewsUiState.Error(r.value)
                }

                is Right -> {
                    //_uiState.value = LatestNewsUiState.Success(r.value)

                    Log.d("TAGt", "r =${r.value}")
                }
            }
        }

        return value
    }

    /*    suspend fun getAllProjectsFlow(): Flow<List<Project>> {
            val list = dao.getAllProjectsFlowEntity()
            return list.map {
                it.map { uit ->
                    fromProjectsEntityToProjects(uit)
                }
            }
        }


        suspend fun addNewProject(projectEntity: ProjectEntity) {
            withContext(Dispatchers.IO) {
                dao.insertNewProject(projectEntity)
            }
        }

        suspend fun deleteNewProject(projectEntity: ProjectEntity) {
            withContext(Dispatchers.IO) {
                dao.deleteNewProject(projectEntity)
            }
        }*/
}
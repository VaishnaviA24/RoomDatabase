package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.MainInfoRepository
import com.capgemini.starterkit.roomdatabase.room.MainInfoEntity
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainInfoRepository
) : ViewModel() {

    // Creates a LiveData that has values collected from the origin Flow.
    val getAllData: LiveData<List<MainInfoEntity>> = repository.allUsersData.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertData(userInfo: MainInfoEntity) = viewModelScope.launch {
        repository.insertData(userInfo)
    }

    fun delete(userid : Int){
        viewModelScope.launch {
            repository.deleteById(userid)
        }
    }

}
package com.capgemini.starterkit.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.starterkit.roomdatabase.repository.UserRepository
import com.capgemini.starterkit.roomdatabase.room.User
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    // Creates a LiveData that has values collected from the origin Flow.
    val getAllData: LiveData<List<User>> = repository.allUsersData.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertData(userInfo: User) = viewModelScope.launch {
        repository.insertData(userInfo)
    }

    fun delete(userid : Int){
        viewModelScope.launch {
            repository.deleteById(userid)
        }
    }

}